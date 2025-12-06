SELECT
  id,
  user_id,
  subject_id,
  score,
  created_at,
  updated_at,
  deleted_at
FROM
  subject_results
  INNER JOIN users
    ON subject_results.user_id = users.id
    AND users.deleted_at IS NULL
WHERE
  subject_results.deleted_at IS NULL
  /*%if subjectResultId != null*/
    AND subject_results.id = /*subjectResultId*/0
  /*%end*/
  /*%if userId != null*/
    AND subject_results.user_id = users.user_id = /* userId */'dummy'
  /*%end*/