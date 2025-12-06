SELECT
  id,
  user_id,
  username,
  email,
  role,
  status,
  created_at,
  updated_at,
  deleted_at
FROM
  users
WHERE
  /*%if userId != null*/
  user_id = /* userId */'dummy'
  /*%end*/