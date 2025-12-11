SELECT
  count(*)
FROM monthly_user_subject_results
  INNER JOIN users
    ON monthly_user_subject_results.user_id = users.user_id
    AND users.deleted_at IS NULL
WHERE
  monthly_user_subject_results.deleted_at IS NULL
  /*%if param.userId != null*/
    AND monthly_user_subject_results.user_id = /* param.userId */1
  /*%end*/
  /*%if param.year != null*/
    AND monthly_user_subject_results.year = /* param.year */2000
  /*%end*/
  /*%if param.month != null*/
    AND monthly_user_subject_results.month = /* param.month */1
  /*%end*/
ORDER BY
  monthly_user_subject_results.year DESC,
  monthly_user_subject_results.month DESC,
  monthly_user_subject_results.total_score DESC,
  monthly_user_subject_results.user_id ASC;