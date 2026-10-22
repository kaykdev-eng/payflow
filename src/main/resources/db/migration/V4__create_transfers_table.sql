CREATE TABLE transfers(
    id BIGSERIAL PRIMARY KEY,
    payer_wallet_id BIGINT NOT NULL CONSTRAINT fk_payer_wallet REFERENCES wallets(id),
    payee_wallet_id BIGINT NOT NULL CONSTRAINT fk_payee_wallet REFERENCES wallets(id),
    amount NUMERIC(19,2) NOT NULL CHECK ( amount >= 0 ),
    status VARCHAR(20) NOT NULL,
    idempotency_key VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
)