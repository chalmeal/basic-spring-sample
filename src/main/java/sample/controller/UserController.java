package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.dto.request.auth.PasswordChangeLoginAfterRequest;
import sample.dto.request.user.UserRegisterRequest;
import sample.dto.request.user.UserRegisterTemporaryRequest;
import sample.dto.request.user.UserSearchRequest;
import sample.dto.response.ErrorResponse;
import sample.dto.response.user.UserSearchResponse;
import sample.service.SecurityService;
import sample.service.UserService;
import sample.utils.JwtUtils;
import sample.utils.Pagination;
import sample.utils.constrains.ValidAccess;
import sample.utils.exception.ExistsResourceException;
import sample.utils.exception.NotFoundException;

/** ユーザーコントローラ */
@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final SecurityService securityService;
    /** ユーザーサービスDI */
    private final UserService userService;

    /**
     * ユーザーIDで取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    @GetMapping("/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getByUserid(@ValidAccess("userId") @PathVariable("user_id") String userId) {
        try {
            return ResponseEntity.ok().body(userService.getByUserId(userId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * ユーザー検索
     * 
     * @param userId     ユーザーID
     * @param username   ユーザー名
     * @param email      メールアドレス
     * @param role       権限
     * @param pageSize   ページサイズ
     * @param pageNumber ページ番号
     * @return ユーザー一覧
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pagination<UserSearchResponse>> search(
            @RequestParam(name = "user_id", required = false) String userId,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "role", required = false) Integer role,
            @RequestParam(name = "page_size", required = true, defaultValue = "30") Integer pageSize,
            @RequestParam(name = "page_number", required = true, defaultValue = "1") Integer pageNumber) {
        // 検索リクエストパラメータ
        UserSearchRequest request = UserSearchRequest.builder()
                .userId(userId)
                .username(username)
                .email(email)
                .role(role)
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();

        return ResponseEntity.ok().body(userService.search(request));
    }

    /**
     * ユーザー仮登録
     * 
     * @param request ユーザー仮登録リクエスト
     * @return
     */
    @PostMapping("/register/temporary")
    public ResponseEntity<?> registerTemporary(@RequestBody @Valid UserRegisterTemporaryRequest request) {
        try {
            userService.registerTemporaryUser(request);

            // ユーザー仮登録成功
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ExistsResourceException e) {
            // メールアドレス重複
            // セキュリティの観点から、ユーザーに明示的にエラーとはしない
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MailSendException e) {
            // メール送信に失敗
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * ユーザー本登録
     * 
     * @param email   メールアドレス
     * @param request ユーザー本登録リクエスト
     * @return
     */
    @PutMapping("/register/{email}")
    public ResponseEntity<?> register(@PathVariable("email") String email,
            @RequestBody @Valid UserRegisterRequest request) {
        try {
            userService.registerUser(email, request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ExistsResourceException e) {
            // ユーザーIDが既に使用されている場合の処理
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * パスワード変更（ログイン後）
     * 
     * @param request パスワード変更リクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/password/change")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeLoginAfterRequest request) {
        try {
            String userId = JwtUtils.getClaimValue("userId");
            securityService.changePassword(request, userId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // 確認用パスワードと新しいパスワードが一致しない場合
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

}
