package sample.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sample.dto.request.user.UserRegisterTemporaryRequest;
import sample.dto.request.user.UserSearchRequest;
import sample.dto.response.ErrorResponse;
import sample.dto.response.user.UserSearchResponse;
import sample.service.UserService;
import sample.utils.Pagination;
import sample.utils.exception.ExistsResourceException;
import sample.utils.exception.NotFoundException;

/** ユーザーコントローラ */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    /** ユーザーサービスDI */
    private final UserService userService;

    /**
     * ユーザーIDで取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getByUserid(@PathVariable("user_id") String userId) {
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

    @PostMapping("/register/temporary")
    public ResponseEntity<?> registerTemporary(@RequestBody @Valid UserRegisterTemporaryRequest request) {
        try {
            userService.registerTemporaryUser(request);

            // ユーザー仮登録成功
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ExistsResourceException e) {
            // メールアドレス重複エラー
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

}
