INSERT INTO users (user_id, username, email, password, role, created_at, updated_at, deleted_at) VALUES
('taro.yamada', '山田太郎', 'taro.yamada@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('hanako.sato', '佐藤花子', 'hanako.sato@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('ichiro.suzuki', '鈴木一郎', 'ichiro.suzuki@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 2, NOW(), NULL, NULL),
('misaki.takahashi', '高橋美咲', 'misaki.takahashi@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 2, NOW(), NULL, NULL),
('kenta.tanaka', '田中健太', 'kenta.tanaka@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('ai.nakamura', '中村愛', 'ai.nakamura@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('naoki.kobayashi', '小林直樹', 'naoki.kobayashi@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 2, NOW(), NULL, NULL),
('mayu.kato', '加藤真由', 'mayu.kato@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('sho.yoshida', '吉田翔', 'sho.yoshida@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL),
('yuko.yamaguchi', '山口優子', 'yuko.yamaguchi@example.com', '$2a$10$abcdefghijklmno1234567890abcdefghi', 1, NOW(), NULL, NULL);
-- パスワードはすべて "password123" をbcryptでハッシュ化したものを使用しています。