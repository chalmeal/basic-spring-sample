SELECT
  user_id,
  username,
  email,
  role
FROM
  users
WHERE
  deleted_at IS NULL
  AND user_id = /* param.user_id */
  AND username LIKE CONCAT('%', /* param.username */, '%')
  AND email LIKE CONCAT('%', /* param.email */, '%')
  AND role = /* param.role */
ORDER BY
  id DESC