INSERT INTO mfa_passcode_info (user_id, passcode, expires_at, created_at)
VALUES (
  /* param.userId */'dummy',
  /* param.passcode */'dummy',
  /* param.expiresAt */'1970-01-01 00:00:00', 
  NOW()
)
ON DUPLICATE KEY UPDATE
    passcode = VALUES(passcode),
    expires_at = VALUES(expires_at),
    updated_at = NOW();