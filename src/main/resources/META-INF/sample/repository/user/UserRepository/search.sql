SELECT
  user_id,
  username,
  email,
  role
FROM
  users
WHERE
  deleted_at IS NULL
  /*%if param.userId != null*/
  AND user_id = /* param.userId */'dummy'
  /*%end*/
  /*%if param.username != null*/
  AND username LIKE CONCAT('%', /* param.username */'dummy', '%')
  /*%end*/
  /*%if param.email != null*/
  AND email LIKE CONCAT('%', /* param.email */'dummy', '%')
  /*%end*/
  /*%if param.role != null*/
  AND role = /* param.role */'dummy'
  /*%end*/
ORDER BY id DESC
