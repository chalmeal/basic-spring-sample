package sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.repository.SystemRepository;

/** システムサービス */
@Service
@RequiredArgsConstructor
public class SystemService {
    /** システムリポジトリDI */
    private final SystemRepository systemRepository;

    /**
     * ヘルスチェック
     */
    @Transactional(readOnly = true)
    public void healthCheck() {
        // DB疎通確認
        systemRepository.healthCheck();
    }

}
