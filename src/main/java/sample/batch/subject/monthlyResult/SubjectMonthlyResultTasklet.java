package sample.batch.subject.monthlyResult;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sample.service.SubjectService;

/** 科目別月次成績集計Tasklet */
@Component
@RequiredArgsConstructor
public class SubjectMonthlyResultTasklet implements Tasklet {
    private final SubjectService subjectService;

    /** 科目別月次成績集計実行 */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 前月の年月を取得
        LocalDate prevMonth = LocalDate.now().minusMonths(1);
        subjectService.aggregateMonthlySubjectResults(prevMonth);

        return RepeatStatus.FINISHED;
    }

}
