SELECT
  id,
  name,
  created_at,
  updated_at,
  deleted_at
FROM
  subjects
WHERE
  /*%if id != null*/
    id = /* id */'dummy'
  /*%end*/