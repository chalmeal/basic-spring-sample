UPDATE users
SET 
  password = /* newPassword */'dummy',
  updated_at = CURRENT_TIMESTAMP
WHERE 
  id = /* usersId */1