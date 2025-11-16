-- ユーザーテーブル
CREATE TABLE IF NOT EXISTS users(
  id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , user_id VARCHAR(50) NOT NULL COMMENT 'ユーザーID'
  , username VARCHAR(100) NOT NULL COMMENT 'ユーザー名'
  , email VARCHAR(100) DEFAULT NULL COMMENT 'メールアドレス'
  , password VARCHAR(500) DEFAULT NULL COMMENT 'パスワード'
  , role INT(1) DEFAULT NULL COMMENT '権限'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_user_id (user_id)
  , UNIQUE KEY uq_email (email)
  , KEY idx_users (email, deleted_at)
) COMMENT='ユーザー';