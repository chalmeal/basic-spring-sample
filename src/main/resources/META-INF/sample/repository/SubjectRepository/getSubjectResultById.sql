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
WHERE
  subject_results.deleted_at IS NULL
  /*%if subjectResultId != null*/
    AND subject_results.id = /*subjectResultId*/0
  /*%end*/
  /*%if userId != null*/
    AND user_id = /* userId */'dummy'
  /*%end*/