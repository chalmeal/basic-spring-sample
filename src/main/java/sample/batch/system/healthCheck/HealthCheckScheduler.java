package sample.batch.system.healthCheck;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/** ヘルスチェックスケジューラ */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "batch.scheduling.system.health-check.enabled")
public class HealthCheckScheduler {
    private final JobLauncher jobLauncher;
    private final Job healthCheckJob;

    /**
     * ヘルスチェックジョブ
     * 実行単位：1分
     * 
     * @throws JobExecutionException ジョブ実行例外
     */
    @Scheduled(cron = "${batch.scheduling.system.health-check.cron}")
    public void run() throws JobExecutionException {
        jobLauncher.run(healthCheckJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters());
    }
}
