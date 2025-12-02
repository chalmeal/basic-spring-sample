UPDATE users
SET 
  password = /* newPassword */'dummy',
  updated_at = CURRENT_TIMESTAMP
WHERE 
  user_id = /* userId */'dummy'