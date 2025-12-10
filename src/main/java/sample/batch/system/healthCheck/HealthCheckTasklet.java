package sample.batch.system.healthCheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sample.service.SystemService;

/** ヘルスチェックTasklet */
@Component
@RequiredArgsConstructor
public class HealthCheckTasklet implements Tasklet {
    private final SystemService systemService;

    /** ヘルスチェック実行 */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        systemService.healthCheck();
        return RepeatStatus.FINISHED;
    }
}
