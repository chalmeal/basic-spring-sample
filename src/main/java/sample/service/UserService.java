package sample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.user.UserGetDto;
import sample.dto.user.UserSearchDto;
import sample.entity.User;
import sample.query.user.UserSearchParam;
import sample.repository.user.UserRepository;
import sample.utils.exception.NotFoundException;

/** ユーザーサービス */
@Service
@RequiredArgsConstructor
public class UserService {
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;

    /**
     * ユーザーIDで取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    @Transactional(readOnly = true)
    public UserGetDto getByUserId(String userId) throws NotFoundException {
        User user = userRepository.getByUserId(userId)
                .orElseThrow(() -> new NotFoundException("ユーザーが見つかりませんでした。", userId));

        return new UserGetDto(user);
    }

    /**
     * ユーザー検索
     * 
     * @param param 検索パラメータ
     * @return ユーザー一覧
     */
    @Transactional(readOnly = true)
    public List<User> search(UserSearchDto dto) {
        UserSearchParam param = UserSearchParam.builder()
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();

        return userRepository.search(param);
    }

}
