package sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.request.user.UserSearchRequest;
import sample.dto.response.user.UserGetResponse;
import sample.dto.response.user.UserSearchResponse;
import sample.entity.User;
import sample.query.user.UserSearchParam;
import sample.repository.user.UserRepository;
import sample.utils.Pagination;
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
    public UserGetResponse getByUserId(String userId) throws NotFoundException {
        // ユーザー取得
        User user = userRepository.getByUserId(userId)
                .orElseThrow(() -> new NotFoundException("ユーザーが見つかりませんでした。", userId));

        return new UserGetResponse(user);
    }

    /**
     * ユーザー検索
     * 
     * @param param 検索パラメータ
     * @return ユーザー一覧
     */
    @Transactional(readOnly = true)
    public Pagination<UserSearchResponse> search(UserSearchRequest request) {
        Pagination<UserSearchResponse> pagination = new Pagination<UserSearchResponse>();
        // 検索パラメータ設定
        UserSearchParam param = UserSearchParam.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .pageSize(request.getPageSize())
                .pageNumber(request.getPageNumber())
                .build();

        // ユーザー検索
        List<User> result = userRepository.search(param);
        List<UserSearchResponse> response = new ArrayList<UserSearchResponse>();
        for (User user : result) {
            response.add(new UserSearchResponse(user));
        }
        // 総件数取得
        int totalCount = userRepository.count(param);

        return pagination.paging(response, totalCount, request.getPageSize());
    }

}
