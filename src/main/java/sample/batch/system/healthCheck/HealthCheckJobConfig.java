package sample.batch.system.healthCheck;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/** ヘルスチェックジョブ設定 */
@Configuration
@RequiredArgsConstructor
public class HealthCheckJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;
    private final HealthCheckTasklet tasklet;

    @Bean
    public Step healthCheckStep() {
        return new StepBuilder("healthCheckStep", jobRepository)
                .tasklet(tasklet, tx)
                .build();
    }

    @Bean
    public Job healthCheckJob() {
        return new JobBuilder("healthCheckJob", jobRepository)
                .start(healthCheckStep())
                .build();
    }
}
