INSERT INTO monthly_user_subject_results (
  user_id,
  year,
  month,
  total_score,
  average_score,
  created_at
)
WITH monthly_user_subject_result_aggregate AS (
  SELECT
    year,
    month,
    user_id,
    SUM(score) AS total_score,
    ROUND(AVG(score), 2) AS avg_score
  FROM subject_results
  WHERE
    deleted_at IS NULL
      AND	year = /* param.year */2000
      AND month = /* param.month */11
  GROUP BY
    year,
    month,
    user_id
)
SELECT
  user_id,
  year,
  month,
  total_score,
  avg_score,
  NOW()
FROM monthly_user_subject_result_aggregate