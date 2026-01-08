package sample.repository;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import sample.repository.query.log.LogEndWriteParam;
import sample.repository.query.log.LogStartWriteParam;

@Dao
@ConfigAutowireable
public interface LogRepository {

    /**
     * ログ書き込み
     * 
     * @param logWriteParam ログ書き込みパラメータ
     */
    @Insert
    public int startWrite(LogStartWriteParam param);

    /**
     * ログ完了書き込み
     * 
     * @param logWriteParam ログ完了書き込みパラメータ
     */
    @Update(sqlFile = true)
    public int endWrite(LogEndWriteParam param);

}
