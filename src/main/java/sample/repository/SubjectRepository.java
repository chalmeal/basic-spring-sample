package sample.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import sample.entity.Subject;

/** 科目DAO */
@Dao
@ConfigAutowireable
public interface SubjectRepository {

    /**
     * IDで取得
     * 
     * @param id 科目ID
     * @return 科目情報
     */
    @Select
    public Optional<Subject> getSubjectById(Long id);

    /**
     * 全科目取得
     * 
     * @return 科目リスト
     */
    @Select
    public List<Subject> fetchAllSubject();

}
