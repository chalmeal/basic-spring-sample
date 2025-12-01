-- ユーザーテーブル
CREATE TABLE IF NOT EXISTS users(
  id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , user_id VARCHAR(50) COMMENT 'ユーザーID'
  , username VARCHAR(100) COMMENT 'ユーザー名'
  , email VARCHAR(100) NOT NULL COMMENT 'メールアドレス'
  , password VARCHAR(500) DEFAULT NULL COMMENT 'パスワード'
  , role INT DEFAULT NULL COMMENT '権限'
  , status INT DEFAULT NULL COMMENT '状態'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_user_id (user_id)
  , UNIQUE KEY uq_email (email)
  , KEY idx_users (email, deleted_at)
) COMMENT='ユーザー';

-- パスワードリセット用トークンテーブル
CREATE TABLE password_reset_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID'
    ,users_id BIGINT UNSIGNED NOT NULL COMMENT 'ユーザーID'
    ,token CHAR(64) NOT NULL UNIQUE COMMENT 'トークン'
    ,expires_at DATETIME NOT NULL COMMENT '有効期限'
    ,used BOOLEAN NOT NULL DEFAULT FALSE COMMENT '使用済みフラグ'
    ,created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時'
    ,updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
    ,CONSTRAINT fk_password_reset_user
        FOREIGN KEY (users_id) REFERENCES users(id)
        ON DELETE CASCADE
    ,KEY idx_password_reset_user (users_id)
    ,KEY idx_password_reset_token_lookup (token, used, expires_at)
) COMMENT='パスワードリセット用トークン';
