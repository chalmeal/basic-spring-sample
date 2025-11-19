package sample.repository;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/** 認証DAO */
@Dao
@ConfigAutowireable
public interface AuthRepository {

    /**
     * ハッシュ化されたパスワードを取得
     * 
     * @param email メールアドレス
     * @return ハッシュ化されたパスワード
     */
    @Select
    public String getHashPassword(String email);
}
