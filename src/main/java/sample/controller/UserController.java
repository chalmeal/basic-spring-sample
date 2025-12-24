package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
import sample.dto.response.user.UserSearchResponse;
import sample.service.SecurityService;
import sample.service.UserService;
import sample.types.user.UserRoleType;
import sample.utils.Pagination;
import sample.utils.SecurityUtils;

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
    public ResponseEntity<?> getByUserid(@PathVariable("user_id") String userId) {
        // 一般ユーザーの場合は自分のユーザー情報のみ取得可能
        if (SecurityUtils.getClaimValue("role").equals(UserRoleType.USER.getRoleName())
                && !userId.equals(SecurityUtils.getClaimValue("userId"))) {
            throw new AccessDeniedException("別ユーザーのユーザー情報は取得できません。");
        }
        return ResponseEntity.ok().body(userService.getByUserId(userId));
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
                .pageNumber(Pagination.pageNumberConvert(pageSize, pageNumber))
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
        userService.registerTemporaryUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
        userService.registerUser(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
        String userId = SecurityUtils.getClaimValue("userId");
        securityService.changePassword(request, userId);
        return ResponseEntity.ok().build();
    }

}
