package sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.response.subject.SubjectGetResponse;
import sample.entity.Subject;
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

}
