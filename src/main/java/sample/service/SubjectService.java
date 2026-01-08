package sample.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sample.dto.request.subject.SubjectResultCsvImportRequest;
import sample.dto.request.subject.SubjectResultMonthlySearchRequest;
import sample.dto.request.subject.SubjectResultSearchRequest;
import sample.dto.request.subject.SubjectResultUserMonthlySearchRequest;
import sample.dto.response.SuccessResponse;
import sample.dto.response.subject.SubjectFetchResponse;
import sample.dto.response.subject.SubjectGetResponse;
import sample.dto.response.subject.SubjectResultGetResponse;
import sample.dto.response.subject.SubjectResultMonthlySearchResponse;
import sample.dto.response.subject.SubjectResultSearchResponse;
import sample.dto.response.subject.SubjectResultUserMonthlySearchResponse;
import sample.entity.Subject;
import sample.entity.SubjectResult;
import sample.entity.SubjectResultMonthlySearch;
import sample.entity.SubjectResultSearch;
import sample.entity.SubjectResultUserMonthlySearch;
import sample.entity.User;
import sample.repository.SubjectRepository;
import sample.repository.UserRepository;
import sample.repository.query.subject.SubjectResultCsvImportParam;
import sample.repository.query.subject.SubjectResultMonthlyGetParam;
import sample.repository.query.subject.SubjectResultMonthlySearchParam;
import sample.repository.query.subject.SubjectResultSearchParam;
import sample.repository.query.subject.SubjectResultUserMonthlyGetParam;
import sample.repository.query.subject.SubjectResultUserMonthlySearchParam;
import sample.types.log.LogExecType;
import sample.utils.Pagination;
import sample.utils.csv.CsvExportUtils;
import sample.utils.csv.CsvImportUtils;
import sample.utils.exception.NotFoundException;
import sample.utils.logger.Logger.Loggable;

/** 科目サービス */
@Service
@RequiredArgsConstructor
public class SubjectService {
    /** 科目リポジトリDI */
    private final SubjectRepository subjectRepository;
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;

    /**
     * IDで取得
     * 
     * @param id 科目ID
     * @return 科目情報
     */
    @Transactional(readOnly = true)
    public SubjectGetResponse getSubjectById(Long id) throws NotFoundException {
        // 科目取得
        Subject subject = subjectRepository.getSubjectById(id)
                .orElseThrow(() -> new NotFoundException("科目が見つかりませんでした。", String.valueOf(id)));

        return new SubjectGetResponse(subject);
    }

    /**
     * 全科目取得
     * 
     * @return 科目リスト
     */
    @Transactional(readOnly = true)
    public List<SubjectFetchResponse> fetchSubject() {
        // 全科目取得
        List<Subject> subjects = subjectRepository.fetchAllSubject();
        List<SubjectFetchResponse> response = new ArrayList<SubjectFetchResponse>();
        for (Subject subject : subjects) {
            response.add(new SubjectFetchResponse(subject));
        }

        return response;
    }

    /**
     * 科目結果をIDで取得
     * 
     * @param subjectResultId 科目結果ID
     * @return 科目結果情報
     */
    @Transactional(readOnly = true)
    public SubjectResultGetResponse getSubjectResultById(Long subjectResultId, String userId) throws NotFoundException {
        // 科目結果取得
        SubjectResult subjectResult = subjectRepository.getSubjectResultById(subjectResultId, userId)
                .orElseThrow(() -> new NotFoundException("科目結果が見つかりませんでした。", String.valueOf(subjectResultId)));

        return new SubjectResultGetResponse(subjectResult);
    }

    /**
     * 科目結果検索
     * 
     * @param request 科目結果検索リクエスト
     * @return 科目結果リスト
     */
    @Transactional(readOnly = true)
    public Pagination<SubjectResultSearchResponse> searchSubjectResult(SubjectResultSearchRequest request) {
        Pagination<SubjectResultSearchResponse> pagination = new Pagination<SubjectResultSearchResponse>();
        // 科目結果検索パラメータ設定
        SubjectResultSearchParam param = SubjectResultSearchParam.builder()
                .userId(request.getUserId())
                .subjectId(request.getSubjectId())
                .scoreFrom(request.getScoreFrom())
                .scoreTo(request.getScoreTo())
                .pageSize(request.getPageSize())
                .pageNumber(request.getPageNumber())
                .build();
        // 科目結果検索
        List<SubjectResultSearch> result = subjectRepository.searchSubjectResult(param);
        List<SubjectResultSearchResponse> response = new ArrayList<SubjectResultSearchResponse>();
        for (SubjectResultSearch subjectResult : result) {
            response.add(new SubjectResultSearchResponse(subjectResult));
        }
        int totalCount = subjectRepository.countSubjectResultSearch(param);

        return pagination.paging(response, totalCount, request.getPageSize());
    }

    /**
     * 科目結果CSV出力
     * 
     * @param request 科目結果CSV出力リクエスト
     * @return CSVファイル
     */
    @Transactional(readOnly = true)
    public byte[] exportSubjectResultsToCsv(SubjectResultSearchRequest request) {
        // 科目結果検索パラメータ設定
        SubjectResultSearchParam param = SubjectResultSearchParam.builder()
                .userId(request.getUserId())
                .subjectId(request.getSubjectId())
                .scoreFrom(request.getScoreFrom())
                .scoreTo(request.getScoreTo())
                .pageSize(request.getPageSize())
                .pageNumber(request.getPageNumber())
                .build();
        // 科目結果検索
        List<SubjectResultSearch> result = subjectRepository.searchSubjectResult(param);

        CsvExportUtils csvUtils = new CsvExportUtils();
        csvUtils.header("ID", "ユーザー名", "科目", "点数");
        for (SubjectResultSearch item : result) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(item.getId()));
            row.add(item.getUserName());
            row.add(item.getSubjectName());
            row.add(String.valueOf(item.getScore()));

            csvUtils.rows(row);
        }

        return csvUtils.export();
    }

    /**
     * 科目結果CSV取込
     * 
     * @param request 科目結果CSV取込リクエスト
     */
    @Transactional
    public void importSubjectResultsFromCsv(String userId, SubjectResultCsvImportRequest request) {
        MultipartFile csvFile = request.getFile();
        CsvImportUtils<SubjectResultCsvImportParam> csvUtils = new CsvImportUtils<SubjectResultCsvImportParam>();

        // CSVヘッダー検証
        String[] expectedHeaders = { "科目ID", "点数" };
        List<SubjectResultCsvImportParam> params = csvUtils.from(csvFile)
                .validHeader(expectedHeaders).parse(columns -> {
                    SubjectResultCsvImportParam param = new SubjectResultCsvImportParam();
                    // ユーザーID
                    Optional<User> user = userRepository.getUserByUserId(userId);
                    // 存在チェック
                    if (CsvImportUtils.isEmptyResource(user, userId, "ユーザーID")) {
                        param.setUserId(userId);
                    }

                    // 科目ID
                    String subjectId = columns[0];
                    // 存在チェック
                    Optional<Subject> subject = subjectRepository.getSubjectById(Long.parseLong(subjectId));
                    if (CsvImportUtils.isEmptyResource(subject, subjectId, expectedHeaders[0])) {
                        param.setSubjectId(subjectId);
                    }

                    // 点数
                    String score = columns[1];
                    // 範囲チェック
                    if (CsvImportUtils.isBetween(score, 0, 100, expectedHeaders[1])) {
                        param.setScore(score);
                    }

                    return param;
                });

        // 科目結果CSV取込登録
        // TODO: ユニーク制約エラーチェック
        subjectRepository.insertSubjectResultForCsv(params);
    }

    /**
     * 科目別月次成績集計検索
     * 
     * @param request 科目別月次成績集計検索リクエスト
     * @return 科目別月次成績集計リスト
     */
    @Transactional(readOnly = true)
    public Pagination<SubjectResultMonthlySearchResponse> searchMonthlySubjectResult(
            SubjectResultMonthlySearchRequest request) {
        Pagination<SubjectResultMonthlySearchResponse> pagination = new Pagination<SubjectResultMonthlySearchResponse>();
        // 科目結果検索パラメータ設定
        SubjectResultMonthlySearchParam param = SubjectResultMonthlySearchParam.builder()
                .subjectId(request.getSubjectId())
                .year(request.getYear())
                .month(request.getMonth())
                .pageSize(request.getPageSize())
                .pageNumber(request.getPageNumber())
                .build();
        // 科目結果検索
        List<SubjectResultMonthlySearch> result = subjectRepository.searchMonthlySubjectResult(param);
        List<SubjectResultMonthlySearchResponse> response = new ArrayList<SubjectResultMonthlySearchResponse>();
        for (SubjectResultMonthlySearch subjectResultMonthly : result) {
            response.add(new SubjectResultMonthlySearchResponse(subjectResultMonthly));
        }
        int totalCount = subjectRepository.countMonthlySubjectResultSearch(param);

        return pagination.paging(response, totalCount, request.getPageSize());
    }

    /**
     * 科目別月次成績集計
     * 
     * @param prevMonth 集計対象月の前月
     */
    @Transactional
    public void aggregateSubjectMonthlyResults(LocalDate prevMonth) {
        // 科目別月次成績集計取得パラメータ設定
        SubjectResultMonthlyGetParam param = SubjectResultMonthlyGetParam.builder()
                .year(prevMonth.getYear())
                .month(prevMonth.getMonthValue()).build();

        // 科目別月次成績集計登録
        subjectRepository.insertMonthlySubjectResult(param);
    }

    /**
     * ユーザー別月次成績集計検索
     * 
     * @param request ユーザー別月次成績集計検索リクエスト
     * @return ユーザー別月次成績集計リスト
     */
    @Transactional(readOnly = true)
    public Pagination<SubjectResultUserMonthlySearchResponse> searchMonthlySubjectUserResult(
            SubjectResultUserMonthlySearchRequest request) {
        Pagination<SubjectResultUserMonthlySearchResponse> pagination = new Pagination<SubjectResultUserMonthlySearchResponse>();
        // ユーザー別月次成績集計検索パラメータ設定
        SubjectResultUserMonthlySearchParam param = SubjectResultUserMonthlySearchParam.builder()
                .userId(request.getUserId())
                .year(request.getYear())
                .month(request.getMonth())
                .pageSize(request.getPageSize())
                .pageNumber(request.getPageNumber())
                .build();
        // 科目結果検索
        List<SubjectResultUserMonthlySearch> result = subjectRepository.searchMonthlySubjectUserResult(param);
        List<SubjectResultUserMonthlySearchResponse> response = new ArrayList<SubjectResultUserMonthlySearchResponse>();
        for (SubjectResultUserMonthlySearch subjectResultUserMonthly : result) {
            response.add(new SubjectResultUserMonthlySearchResponse(subjectResultUserMonthly));
        }
        int totalCount = subjectRepository.countMonthlySubjectUserResultSearch(param);

        return pagination.paging(response, totalCount, request.getPageSize());
    }

    /**
     * ユーザー別月次成績集計
     * 
     * @param prevMonth 集計対象月の前月
     */
    @Loggable(value = "AggregateSubjectMonthlyUserResult", execType = LogExecType.BATCH)
    public SuccessResponse aggregateSubjectMonthlyUserResult(LocalDate prevMonth) {
        // 科目別月次成績集計取得パラメータ設定
        SubjectResultUserMonthlyGetParam param = SubjectResultUserMonthlyGetParam.builder()
                .year(prevMonth.getYear())
                .month(prevMonth.getMonthValue()).build();

        // ユーザー別月次成績集計登録
        subjectRepository.insertMonthlySubjectUserResult(param);

        String target = String.format("%d年%02d月", prevMonth.getYear(), prevMonth.getMonthValue());

        return new SuccessResponse("ユーザー別月次成績集計が完了しました。", target);
    }
}
