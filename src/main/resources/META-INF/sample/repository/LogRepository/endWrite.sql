UPDATE logs
SET result = /* param.result */1
  , message = /* param.message */'message'
  , completed_at = NOW()
WHERE id = /* param.id */1;