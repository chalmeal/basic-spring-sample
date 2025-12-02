package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sample.dto.response.ErrorResponse;
import sample.service.SubjectService;
import sample.types.user.UserRoleType;
import sample.utils.JwtUtils;
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

}
