package sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.request.log.LogWriteRequest;
import sample.repository.LogRepository;
import sample.repository.query.log.LogEndWriteParam;
import sample.repository.query.log.LogStartWriteParam;

/** ログサービス */
@Service
@RequiredArgsConstructor
public class LogService {
    /** ログリポジトリDI */
    private final LogRepository logRepository;

    /**
     * ログ先行書込み処理
     * 
     * @param request ログ書込みリクエスト
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long startWriteLog(LogWriteRequest request) {
        LogStartWriteParam logParam = new LogStartWriteParam();
        logParam.setExecutorId(request.getExecutorId());
        logParam.setProcessName(request.getProcessName());
        logParam.setExecType(request.getExecType());
        logParam.setResult(request.getResult());
        logParam.setStartedAt(request.getStartedAt());

        logRepository.startWrite(logParam);

        return logParam.getId();
    }

    /**
     * ログ終了書込み処理
     * 
     * @param request ログ終了書込みリクエスト
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void endWriteLog(LogWriteRequest request) {
        LogEndWriteParam logParam = LogEndWriteParam.builder()
                .id(request.getId())
                .result(request.getResult())
                .message(request.getMessage())
                .completedAt(request.getCompletedAt())
                .build();

        logRepository.endWrite(logParam);
    }
}
