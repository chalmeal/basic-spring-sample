SELECT
  id,
  users_id,
  subject_id,
  score,
  created_at,
  updated_at,
  deleted_at
FROM
  subject_results
WHERE
  deleted_at IS NULL
  /*%if subjectResultId != null*/
    AND id = /*subjectResultId*/0
  /*%end*/
  /*%if userId != null*/
    AND users_id = (SELECT id FROM users WHERE user_id = /* userId */'dummy')
  /*%end*/