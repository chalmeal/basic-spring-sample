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
  subject_results.id,
  target_subject_users.username AS user_name,
  subjects.name AS subject_name,
  subject_results.score
FROM subject_results
  INNER JOIN subjects
    ON subject_results.subject_id = subjects.id
    AND subjects.deleted_at IS NULL
  INNER JOIN target_subject_users
    ON subject_results.users_id = target_subject_users.id
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
LIMIT /* param.pageSize */30 OFFSET /* param.pageNumber */0;