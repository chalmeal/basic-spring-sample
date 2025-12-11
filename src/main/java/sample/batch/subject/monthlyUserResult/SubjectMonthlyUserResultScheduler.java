package sample.batch.subject.monthlyUserResult;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/** ユーザー別月次成績集計スケジューラ */
@Component
@RequiredArgsConstructor
@ConditionalOnBooleanProperty(value = "batch.scheduling.subject.monthly-user-result.enabled")
public class SubjectMonthlyUserResultScheduler {
    private final JobLauncher jobLauncher;
    @Qualifier("subjectMonthlyUserResultJob")
    private final Job subjectMonthlyUserResultJob;

    /**
     * ユーザー別月次成績集計ジョブ
     * 実行単位：毎月1日0時0分
     * 
     * @throws Exception ジョブ実行例外
     */
    @Scheduled(cron = "${batch.scheduling.subject.monthly-user-result.cron}")
    public void run() throws Exception {
        jobLauncher.run(subjectMonthlyUserResultJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters());
    }

}
