SELECT
  count(*)
FROM monthly_subject_results
  INNER JOIN subjects
    ON monthly_subject_results.subject_id = subjects.id
    AND subjects.deleted_at IS NULL
WHERE
  monthly_subject_results.deleted_at IS NULL
  /*%if param.subjectId != null*/
    AND monthly_subject_results.subject_id = /* param.subjectId */1
  /*%end*/
  /*%if param.year != null*/
    AND monthly_subject_results.year = /* param.year */2000
  /*%end*/
  /*%if param.month != null*/
    AND monthly_subject_results.month = /* param.month */1
  /*%end*/
ORDER BY
  monthly_subject_results.year DESC,
  monthly_subject_results.month DESC,
  monthly_subject_results.subject_id ASC