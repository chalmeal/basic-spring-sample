package sample.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sample.dto.user.UserGetDto;
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
    public ResponseEntity<UserGetDto> getByUserid(@PathVariable("user_id") String userId) {
        try {
            return ResponseEntity.ok().body(userService.getByUserId(userId));
        } catch (NotFoundException e) {
            e.getMessage();
            return ResponseEntity.notFound().build();
        }
    }

}
