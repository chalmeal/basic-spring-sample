SELECT
  user_id,
  username,
  email,
  role,
  created_at,
  updated_at,
  deleted_at
FROM
  users
WHERE
  user_id = /* param.user_id */