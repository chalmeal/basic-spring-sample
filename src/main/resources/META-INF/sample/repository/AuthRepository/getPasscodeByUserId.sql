SELECT
  id,
  user_id,
  passcode,
  expires_at
FROM mfa_passcode_info
WHERE user_id = /* userId */'dummy'