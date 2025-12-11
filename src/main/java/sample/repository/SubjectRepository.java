package sample.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.BatchInsert;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import sample.entity.Subject;
import sample.entity.SubjectResult;
import sample.entity.SubjectResultMonthlySearch;
import sample.entity.SubjectResultSearch;
import sample.repository.query.subject.SubjectResultCsvImportParam;
import sample.repository.query.subject.SubjectResultMonthlyGetParam;
import sample.repository.query.subject.SubjectResultMonthlySearchParam;
import sample.repository.query.subject.SubjectResultSearchParam;
import sample.repository.query.subject.SubjectResultUserMonthlyGetParam;

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

    /**
     * 科目結果をIDで取得
     * 
     * @param subjectResultId 科目結果ID
     * @param userId          ユーザーID（管理者の場合はnull）
     * @return 科目結果情報
     */
    @Select
    public Optional<SubjectResult> getSubjectResultById(Long subjectResultId, String userId);

    /**
     * 科目結果検索
     * 
     * @param param 科目結果検索パラメータ
     * @return 科目結果リスト
     */
    @Select
    public List<SubjectResultSearch> searchSubjectResult(SubjectResultSearchParam param);

    /**
     * 科目結果検索件数取得
     * 
     * @param param 科目結果検索パラメータ
     * @return 科目結果件数
     */
    @Select
    public int countSubjectResultSearch(SubjectResultSearchParam param);

    /**
     * 科目別月次成績集計検索
     * 
     * @param param 科目別月次成績集計検索パラメータ
     * @return 科目別月次成績集計リスト
     */
    @Select
    public List<SubjectResultMonthlySearch> searchMonthlySubjectResult(SubjectResultMonthlySearchParam param);

    /**
     * 科目成績CSV取込登録
     * 
     * @param params 科目成績CSV取込パラメータ
     * @return 登録結果
     */
    @BatchInsert(sqlFile = true)
    public int[] insertSubjectResultForCsv(List<SubjectResultCsvImportParam> params);

    /**
     * 科目別月次成績集計登録
     * 
     * @param param 科目別月次成績集計結果取得クエリパラメータ
     * @return 登録結果
     */
    @Insert(sqlFile = true)
    public int insertMonthlySubjectResult(SubjectResultMonthlyGetParam param);

    /**
     * ユーザー別月次成績集計登録
     * 
     * @param param 科目別月次成績集計取得クエリパラメータ
     * @return 登録結果
     */
    @Insert(sqlFile = true)
    public int insertMonthlySubjectUserResult(SubjectResultUserMonthlyGetParam param);

}
