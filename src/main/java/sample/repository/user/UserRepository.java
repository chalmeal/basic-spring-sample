package sample.repository.user;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import sample.entity.User;
import sample.query.user.UserRegisterParam;
import sample.query.user.UserRegisterTemporaryParam;
import sample.query.user.UserSearchParam;

/** ユーザーDAO */
@Dao
@ConfigAutowireable
public interface UserRepository {
    /**
     * ユーザーIDで取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    @Select
    public Optional<User> getByUserId(String userId);

    /**
     * メールアドレスで取得
     * 
     * @param email メールアドレス
     * @return ユーザーID
     */
    @Select
    public Long getIdByEmail(String email);

    /**
     * ユーザー検索
     * 
     * @param param 検索パラメータ
     * @return ユーザー一覧
     */
    @Select
    public List<User> search(UserSearchParam param);

    /**
     * 総件数取得
     * 
     * @param param 検索パラメータ
     * @return 総件数
     */
    @Select
    public int count(UserSearchParam param);

    /**
     * ユーザー仮登録
     * 
     * @param param ユーザー仮登録パラメータ
     * @return 登録結果
     */
    @Insert(sqlFile = true)
    public int registerTemporary(UserRegisterTemporaryParam param);

    /**
     * ユーザー登録
     * 
     * @param param ユーザー登録パラメータ
     * @return 登録結果
     */
    @Update(sqlFile = true)
    public int register(UserRegisterParam param);

}
