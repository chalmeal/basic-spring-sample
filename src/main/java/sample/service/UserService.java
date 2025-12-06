package sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.dto.request.user.UserRegisterRequest;
import sample.dto.request.user.UserRegisterTemporaryRequest;
import sample.dto.request.user.UserSearchRequest;
import sample.dto.response.user.UserGetResponse;
import sample.dto.response.user.UserSearchResponse;
import sample.entity.User;
import sample.repository.UserRepository;
import sample.repository.query.user.UserRegisterParam;
import sample.repository.query.user.UserRegisterTemporaryParam;
import sample.repository.query.user.UserSearchParam;
import sample.types.user.UserRoleType;
import sample.types.user.UserStatusType;
import sample.utils.MailUtils;
import sample.utils.Pagination;
import sample.utils.exception.NotFoundException;
import sample.utils.exception.ResourceExistsException;

/** ユーザーサービス */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    /** パスワードハッシュサービスDI */
    private final SecurityService passwordHashService;
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;
    /** メール送信ユーティリティDI */
    private final MailUtils mailUtils;
    /** 登録リンク */
    @Value("${spring.mail.properties.register-link}")
    private String registerLink;

    /**
     * ユーザーIDで取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    @Transactional(readOnly = true)
    public UserGetResponse getByUserId(String userId) throws NotFoundException {
        // ユーザー取得
        User user = userRepository.getUserByUserId(userId)
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
        List<User> result = userRepository.searchUser(param);
        List<UserSearchResponse> response = new ArrayList<UserSearchResponse>();
        for (User user : result) {
            response.add(new UserSearchResponse(user));
        }
        // 総件数取得
        int totalCount = userRepository.countUserSearch(param);

        return pagination.paging(response, totalCount, request.getPageSize());
    }

    /**
     * ユーザー仮登録
     * 
     * @param request ユーザー仮登録リクエスト
     */
    @Transactional
    public void registerTemporaryUser(UserRegisterTemporaryRequest request) throws RuntimeException {
        try {
            // ユーザー仮登録パラメータ設定
            UserRegisterTemporaryParam param = UserRegisterTemporaryParam.builder()
                    .email(request.getEmail())
                    .status(UserStatusType.TEMPORARY.getValue())
                    .build();
            userRepository.temporaryRegisterUser(param);

            String mailBody = String.format("""
                        仮登録が完了しました。\n
                        以下のリンクから本登録を行ってください。\n
                        \n
                        %s
                    """, registerLink);
            mailUtils.sendMail(MailUtils.MailSenderObject.builder()
                    .to(request.getEmail())
                    .subject("仮登録完了のお知らせ")
                    .body(mailBody)
                    .build());
        } catch (DuplicateKeyException e) {
            // メールアドレスが既に登録済みの場合はメールを送信せずに処理を中断
            log.warn("既に登録済みのメールアドレスが仮登録を行いました。: {}", request.getEmail());
        }
    }

    /**
     * ユーザー登録
     * 
     * @param request ユーザー登録リクエスト
     */
    @Transactional
    public void registerUser(String email, UserRegisterRequest request) throws RuntimeException {
        try {
            Long id = userRepository.getIdByEmail(email);
            UserRegisterParam param = UserRegisterParam.builder()
                    .id(id)
                    .userId(request.getUserId())
                    .username(request.getUsername())
                    .password(passwordHashService.hashPassword(request.getPassword()))
                    .role(UserRoleType.USER.getValue())
                    .status(UserStatusType.REGISTERED.getValue())
                    .build();

            userRepository.registerUser(param);
        } catch (DuplicateKeyException e) {
            // ユーザーIDが既に使用されている場合
            throw new ResourceExistsException("ユーザーIDが既に使用されています。", request.getUserId());
        }
    }

}
