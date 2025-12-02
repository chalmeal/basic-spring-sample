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

}
