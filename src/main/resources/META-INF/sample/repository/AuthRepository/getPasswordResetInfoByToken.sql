SELECT
  id,
  users_id,
  token,
  expires_at,
  used,
  created_at,
  updated_at
FROM
  password_reset_info
WHERE
  /*%if token != null*/
    token = /* token */'dummy'
  /*%end*/