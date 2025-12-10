package sample.batch.subject.monthlyResult;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/** 科目別月次成績集計ジョブ設定 */
@Configuration
@RequiredArgsConstructor
public class SubjectMonthlyJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;
    private final SubjectMonthlyResultTasklet tasklet;

    @Bean
    public Step subjectMonthlyResultStep() {
        return new StepBuilder("subjectMonthlyResultStep", jobRepository)
                .tasklet(tasklet, tx)
                .build();
    }

    @Bean(name = "subjectMonthlyResultJob")
    public Job subjectMonthlyResultJob() {
        return new JobBuilder("subjectMonthlyResultJob", jobRepository)
                .start(subjectMonthlyResultStep())
                .build();
    }
}
