package sample.repository.user;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sample.entity.User;
import sample.query.user.UserSearchParam;

/** ユーザーDAO */
@Dao
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
     * ユーザー検索
     * 
     * @param param 検索パラメータ
     * @return ユーザー一覧
     */
    @Select
    public List<User> search(UserSearchParam param);

}
