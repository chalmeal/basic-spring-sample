package sample.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sample.dto.request.user.UserSearchRequest;
import sample.dto.response.ErrorResponse;
import sample.dto.response.user.UserSearchResponse;
import sample.service.UserService;
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
     * @param request 検索リクエスト
     * @return ユーザー一覧
     */
    @GetMapping
    public ResponseEntity<List<UserSearchResponse>> search(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer role) {
        // 検索リクエストパラメータ
        UserSearchRequest request = UserSearchRequest.builder()
                .userId(userId)
                .username(username)
                .email(email)
                .role(role)
                .build();

        return ResponseEntity.ok().body(userService.search(request));
    }

}
