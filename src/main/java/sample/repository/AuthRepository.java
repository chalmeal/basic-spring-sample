package sample.repository;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SqlLogType;

import sample.entity.MFAPasscodeInfo;
import sample.entity.PasswordResetInfo;
import sample.repository.query.auth.MFAPasscodeInfoCreateParam;

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
    public String getHashPasswordByEmail(String email);

    /**
     * ハッシュ化されたパスワードを取得（ユーザーID指定）
     * 
     * @param userId ユーザーID
     * @return ハッシュ化されたパスワード
     */
    @Select(sqlLog = SqlLogType.NONE)
    public String getHashPasswordByUserId(String userId);

    /**
     * パスコードを取得（ユーザーID指定）
     * 
     * @param userId ユーザーID
     * @return パスコード
     */
    @Select(sqlLog = SqlLogType.NONE)
    public MFAPasscodeInfo getPasscodeByUserId(String userId);

    /**
     * パスワードリセット情報をトークンで取得
     * 
     * @param token パスワードリセットトークン
     * @return パスワードリセット情報
     */
    @Select(sqlLog = SqlLogType.NONE)
    public Optional<PasswordResetInfo> getPasswordResetInfoByToken(String token);

    /**
     * パスコードを保持
     * 
     * @param param パスコード作成パラメータ
     * @return 登録結果
     */
    @Insert(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int createPasscode(MFAPasscodeInfoCreateParam param);

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
     * パスワードを更新（ユーザーID指定）
     * 
     * @param userId      ユーザーID
     * @param newPassword ハッシュ化されたパスワード
     * @return 更新件数
     */
    @Update(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int updatePasswordByUserId(String userId, String newPassword);

    /**
     * パスワードリセット情報を使用済みに設定
     * 
     * @param id パスワードリセット情報のID（PK）
     * @return 更新件数
     */
    @Update(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int updatePasswordResetInfoAsUsed(Long id);

    /**
     * パスコード情報を削除
     * 
     * @param userId ユーザーID
     * @return 削除件数
     */
    @Delete(sqlFile = true, sqlLog = SqlLogType.NONE)
    public int deletePasscodeInfo(String userId);

}
