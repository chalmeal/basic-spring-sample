package sample.repository;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SqlLogType;

import sample.entity.PasswordResetInfo;

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
    @Select(sqlLog = SqlLogType.NONE)
    public String getHashPassword(String email);

    /**
     * パスワードリセット情報をトークンで取得
     * 
     * @param token パスワードリセットトークン
     * @return パスワードリセット情報
     */
    @Select(sqlLog = SqlLogType.NONE)
    public Optional<PasswordResetInfo> getPasswordResetInfoByToken(String token);

    /**
     * パスワードリセット情報を保持
     * 
     * @param usersId   ユーザーテーブルのID（PK）
     * @param token     パスワードリセットトークン
     * @param expiresAt 有効期限
     * @return
     */
    @Insert(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int createPasswordResetInfo(Long usersId, String token, String expiresAt);

    /**
     * パスワードを更新
     * 
     * @param usersId     ユーザーテーブルのID（PK）
     * @param newPassword ハッシュ化されたパスワード
     * @return 更新件数
     */
    @Update(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int updatePassword(Long usersId, String newPassword);

    /**
     * パスワードリセット情報を使用済みに設定
     * 
     * @param id パスワードリセット情報のID（PK）
     * @return 更新件数
     */
    @Update(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int updatePasswordResetInfoAsUsed(Long id);

}
