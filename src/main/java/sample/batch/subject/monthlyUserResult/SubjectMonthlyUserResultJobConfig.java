package sample.batch.subject.monthlyUserResult;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/** ユーザー別月次成績集計ジョブ設定 */
@Configuration
@RequiredArgsConstructor
public class SubjectMonthlyUserResultJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;
    private final SubjectMonthlyUserResultTasklet tasklet;

    @Bean
    public Step subjectMonthlyUserResultStep() {
        return new StepBuilder("subjectMonthlyUserResultStep", jobRepository)
                .tasklet(tasklet, tx)
                .build();
    }

    @Bean(name = "subjectMonthlyUserResultJob")
    public Job subjectMonthlyUserResultJob() {
        return new JobBuilder("subjectMonthlyUserResultJob", jobRepository)
                .start(subjectMonthlyUserResultStep())
                .build();
    }
}
