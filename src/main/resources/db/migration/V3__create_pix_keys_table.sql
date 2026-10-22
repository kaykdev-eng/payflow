CREATE TABLE pix_keys(
    id BIGSERIAL PRIMARY KEY,
    wallet_id BIGINT NOT NULL CONSTRAINT fk_wallet REFERENCES wallets(id),
    key_type VARCHAR(20) NOT NULL,
    key_value VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
)