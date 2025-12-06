WITH target_subject_users AS (
  SELECT
    id, user_id, username
  FROM users
  WHERE
    users.deleted_at IS NULL
    /*%if param.userId != null*/
      AND user_id = /* param.userId */'dummy'
    /*%end*/
)

SELECT
  count(*)
FROM subject_results
  INNER JOIN subjects
    ON subject_results.subject_id = subjects.id
    AND subjects.deleted_at IS NULL
  INNER JOIN target_subject_users
    ON subject_results.user_id = target_subject_users.user_id
WHERE
  subject_results.deleted_at IS NULL
  /*%if param.subjectId != null*/
    AND subjects.id = /* param.subjectId */'dummy'
  /*%end*/
  /*%if param.scoreFrom != null*/
    AND subject_results.score >= /* param.scoreFrom */0
  /*%end*/
  /*%if param.scoreTo != null*/
    AND subject_results.score <= /* param.scoreTo */0
  /*%end*/
ORDER BY
  target_subject_users.id,
  subjects.id,
  subject_results.id