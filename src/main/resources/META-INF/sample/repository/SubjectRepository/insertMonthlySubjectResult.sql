INSERT INTO monthly_subject_results (
  subject_id,
  year,
  month,
  max_score,
  min_score,
  avg_score,
  created_at
)
WITH monthly_subject_result_aggregate AS (
  SELECT
      year,
      month,
      subject_id,
      MAX(score) AS max_score,
      MIN(score) AS min_score,
      ROUND(AVG(score), 2) AS avg_score
  FROM subject_results
  WHERE
      deleted_at IS NULL
      AND year = /* param.year */2000
      AND month = /* param.month */1
  GROUP BY year, month, subject_id
)
SELECT
  subject_id,
  year,
  month,
  max_score,
  min_score,
  avg_score,
  NOW()
FROM monthly_subject_result_aggregate
