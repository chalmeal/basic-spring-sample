INSERT INTO password_reset_info
(
    users_id,
    token,
    expires_at,
    used,
    created_at,
    updated_at
)
VALUES
(
    /* usersId */1,
    /* token */'dummy',
    /* expiresAt */'dummy',
    FALSE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);