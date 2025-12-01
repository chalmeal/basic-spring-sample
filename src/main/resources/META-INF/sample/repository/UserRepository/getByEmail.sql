SELECT
  id,
  user_id,
  username,
  role
FROM
  users
WHERE
  /*%if email != null*/
  email = /* email */'dummy'
  /*%end*/