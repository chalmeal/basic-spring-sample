package sample.repository;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/** システムDAO */
@Dao
@ConfigAutowireable
public interface SystemRepository {
    /**
     * ヘルスチェック
     * 
     * @return 1
     */
    @Select
    public Integer healthCheck();

}
