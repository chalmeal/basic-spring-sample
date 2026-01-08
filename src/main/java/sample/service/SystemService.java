package sample.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.response.SuccessResponse;
import sample.repository.SystemRepository;
import sample.types.log.LogExecType;
import sample.utils.logger.Logger.Loggable;

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
    @Loggable(value = "HealthCheck", execType = LogExecType.BATCH)
    public SuccessResponse healthCheck() {
        // DB疎通確認
        systemRepository.healthCheck();

        return new SuccessResponse("ヘルスチェック成功", LocalDateTime.now().toString());
    }

}
