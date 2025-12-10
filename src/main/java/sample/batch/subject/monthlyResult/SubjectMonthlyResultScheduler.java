package sample.batch.subject.monthlyResult;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/** 科目別月次成績集計スケジューラ */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "batch.scheduling.subject.monthly-result.enabled")
public class SubjectMonthlyResultScheduler {
    private final JobLauncher jobLauncher;
    @Qualifier("subjectMonthlyResultJob")
    private final Job subjectMonthlyResultJob;

    /**
     * 科目別月次成績集計ジョブ
     * 実行単位：毎月1日0時0分
     * 
     * @throws JobExecutionException ジョブ実行例外
     */
    @Scheduled(cron = "${batch.scheduling.subject.monthly-result.cron}")
    public void run() throws JobExecutionException {
        jobLauncher.run(subjectMonthlyResultJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters());
    }
}
