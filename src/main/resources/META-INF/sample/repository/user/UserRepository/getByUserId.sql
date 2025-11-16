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
  /*%if userId != null*/
  user_id = /* userId */'dummy'
  /*%end*/