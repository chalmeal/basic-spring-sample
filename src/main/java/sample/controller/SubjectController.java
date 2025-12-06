package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sample.dto.request.subject.SubjectResultCsvImportRequest;
import sample.dto.request.subject.SubjectResultSearchRequest;
import sample.dto.response.ErrorResponse;
import sample.service.SubjectService;
import sample.types.user.UserRoleType;
import sample.utils.JwtUtils;
import sample.utils.Pagination;
import sample.utils.csv.CsvExportUtils;
import sample.utils.exception.CsvExportException;
import sample.utils.exception.CsvImportException;
import sample.utils.exception.NotFoundException;

/** 科目コントローラー */
@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    /** 科目サービスDI */
    private final SubjectService subjectService;

    /**
     * 科目IDで取得
     * 
     * @param id 科目ID
     * @return 科目情報
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getSubjectById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(subjectService.getSubjectById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * 科目一覧取得（全件）
     * 
     * @return 科目一覧
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> fetchSubject() {
        return ResponseEntity.ok().body(subjectService.fetchSubject());
    }

    /**
     * 科目結果IDで取得
     * 
     * @param subjectResultId 科目結果ID
     * @return 科目結果情報
     */
    @GetMapping("/result/{subject_result_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getSubjectResultById(@PathVariable("subject_result_id") Long subjectResultId) {
        try {
            String userId = JwtUtils.getClaimValue("userId");
            // 管理者の場合は全ユーザーの科目結果取得可のため、ユーザーIDをnullに設定
            if (JwtUtils.getClaimValue("role").equals(UserRoleType.ADMIN.getRoleName())) {
                userId = null;
            }

            return ResponseEntity.ok().body(subjectService.getSubjectResultById(subjectResultId, userId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * 科目結果検索
     * 
     * @param userId     ユーザーID
     * @param subjectId  科目ID
     * @param scoreFrom  スコア（開始）
     * @param scoreTo    スコア（終了）
     * @param pageSize   ページサイズ
     * @param pageNumber ページ番号
     * @return 科目結果リスト
     */
    @GetMapping("/result")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> searchSubjectResult(
            @RequestParam(name = "user_id", required = false) String userId,
            @RequestParam(name = "subject_id", required = false) String subjectId,
            @RequestParam(name = "score_from", required = false) String scoreFrom,
            @RequestParam(name = "score_to", required = false) String scoreTo,
            @RequestParam(name = "page_size", required = true, defaultValue = "30") Integer pageSize,
            @RequestParam(name = "page_number", required = true, defaultValue = "1") Integer pageNumber) {
        // 一般ユーザーの場合は自分の科目結果のみ取得可能
        if (JwtUtils.getClaimValue("role").equals(UserRoleType.USER.getRoleName())) {
            // TODO: 不正アクセス例外
            userId = JwtUtils.getClaimValue("userId");
        }
        // 検索リクエストパラメータ
        SubjectResultSearchRequest request = SubjectResultSearchRequest.builder()
                .userId(userId)
                .subjectId(subjectId)
                .scoreFrom(scoreFrom)
                .scoreTo(scoreTo)
                .pageSize(pageSize)
                .pageNumber(Pagination.pageNumberConvert(pageSize, pageNumber))
                .build();

        return ResponseEntity.ok().body(subjectService.searchSubjectResult(request));
    }

    /**
     * 科目結果CSV出力
     * 
     * @param request 科目結果CSV出力リクエスト
     * @return CSVファイル
     */
    @PostMapping("/csv/export")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> exportSubjectResultsToCsv(@RequestBody @Valid SubjectResultSearchRequest request) {
        try {
            // 一般ユーザーの場合は自分の科目結果のみ取得可能
            if (JwtUtils.getClaimValue("role").equals(UserRoleType.USER.getRoleName())) {
                // TODO: 不正アクセス例外
                request.setUserId(JwtUtils.getClaimValue("userId"));
            }
            request.setPageNumber(Pagination.pageNumberConvert(request.getPageSize(), request.getPageNumber()));
            byte[] csvData = subjectService.exportSubjectResultsToCsv(request);

            return CsvExportUtils.csvExportResponse(csvData, "科目成績.csv");
        } catch (CsvExportException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * 科目結果CSV取込
     * 
     * @param userId  ユーザーID
     * @param request 科目結果CSV取込リクエスト
     * @return
     */
    @PostMapping(value = "/csv/import/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> importSubjectResultsFromCsv(
            @PathVariable("user_id") String userId,
            @Valid @ModelAttribute SubjectResultCsvImportRequest request) {
        try {
            // 一般ユーザーの場合は自分の科目結果のみ取得可能
            if (JwtUtils.getClaimValue("role").equals(UserRoleType.USER.getRoleName())
                    && !userId.equals(JwtUtils.getClaimValue("userId"))) {
                // TODO: 不正アクセス例外
                userId = JwtUtils.getClaimValue("userId");
            }

            subjectService.importSubjectResultsFromCsv(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NotFoundException e) {
            // リソースが存在しない場合
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (CsvImportException e) {
            // CSV取込エラー
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

}
