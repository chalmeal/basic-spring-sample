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

-- 多要素認証OTP管理テーブル
CREATE TABLE IF NOT EXISTS mfa_passcode_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID'
    ,user_id VARCHAR(50) NOT NULL COMMENT 'ユーザーID'
    ,passcode CHAR(6) NOT NULL COMMENT 'パスコード'
    ,expires_at DATETIME NOT NULL COMMENT '有効期限'
    ,created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時'
    ,updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
    ,CONSTRAINT fk_user_mfa_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
    ,UNIQUE KEY uq_user_mfa_active_passcode (user_id)
    ,KEY idx_mfa_user (user_id)
    ,KEY idx_mfa_code_lookup (passcode, expires_at)
) COMMENT='多要素認証OTP';

-- パスワードリセット用トークンテーブル
CREATE TABLE IF NOT EXISTS password_reset_info (
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

-- 科目テーブル
CREATE TABLE IF NOT EXISTS subjects (
  id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , name VARCHAR(100) NOT NULL COMMENT '科目名'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_subject_name (name)
  , KEY idx_subject_name (name)
) COMMENT='科目';

-- 科目別成績テーブル
CREATE TABLE IF NOT EXISTS subject_results (
  id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , year INT NOT NULL COMMENT '年度'
  , month INT NOT NULL COMMENT '月'
  , user_id VARCHAR(50) COMMENT 'ユーザーID'
  , subject_id BIGINT unsigned NOT NULL COMMENT '科目ID'
  , score INT NOT NULL COMMENT '得点'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_study_result_year_month_user_subject (year, month, user_id, subject_id)
  , CONSTRAINT fk_study_result_user
      FOREIGN KEY (user_id) REFERENCES users(user_id)
      ON DELETE CASCADE
  , CONSTRAINT fk_study_result_subject
      FOREIGN KEY (subject_id) REFERENCES subjects(id)
      ON DELETE CASCADE
  , KEY idx_study_result_user (user_id)
) COMMENT='学習成績';

-- 科目別月次成績集計テーブル
CREATE TABLE IF NOT EXISTS monthly_subject_results (
  id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , subject_id BIGINT unsigned NOT NULL COMMENT '科目ID'
  , year INT NOT NULL COMMENT '年度'
  , month INT NOT NULL COMMENT '月'
  , max_score INT NOT NULL COMMENT '最高得点'
  , min_score INT NOT NULL COMMENT '最低得点'
  , avg_score DECIMAL(5,2) NOT NULL COMMENT '平均得点'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_monthly_subject_results_subject_year_month (subject_id, year, month)
  , KEY idx_monthly_subject_results_subject_year_month (subject_id, year, month)
) COMMENT='科目別月次成績集計';

-- ユーザー別月次成績集計テーブル
CREATE TABLE IF NOT EXISTS monthly_user_subject_results (
  id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , user_id VARCHAR(50) COMMENT 'ユーザーID'
  , year INT NOT NULL COMMENT '年度'
  , month INT NOT NULL COMMENT '月'
  , total_score INT NOT NULL COMMENT '総得点'
  , average_score DECIMAL(5,2) NOT NULL COMMENT '平均得点'
  , result_file_path VARCHAR(255) DEFAULT NULL COMMENT '成績ファイルパス'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , PRIMARY KEY (id)
  , UNIQUE KEY uq_monthly_user_subject_results_user_year_month (user_id, year, month)
  , CONSTRAINT fk_monthly_user_subject_results_user
      FOREIGN KEY (user_id) REFERENCES users(user_id)
      ON DELETE CASCADE
  , KEY idx_monthly_user_subject_results_user (user_id)
) COMMENT='ユーザー別月次成績集計';