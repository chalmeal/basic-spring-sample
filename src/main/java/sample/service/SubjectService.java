package sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.response.subject.SubjectFetchResponse;
import sample.dto.response.subject.SubjectGetResponse;
import sample.dto.response.subject.SubjectResultGetResponse;
import sample.entity.Subject;
import sample.entity.SubjectResult;
import sample.repository.SubjectRepository;
import sample.utils.exception.NotFoundException;

/** 科目サービス */
@Service
@RequiredArgsConstructor
public class SubjectService {
    /** 科目リポジトリDI */
    private final SubjectRepository subjectRepository;

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

}
