UPDATE users
SET 
  user_id = /* param.userId */'dummy',
  username = /* param.username */'dummy',
  password = /* param.password */'dummy',
  role = /* param.role */1,
  status = /* param.status */1,
  updated_at = CURRENT_TIMESTAMP
WHERE 
  id = /* param.id */1