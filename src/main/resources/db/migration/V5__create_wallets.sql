CREATE TABLE wallets (
    id         UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID          NOT NULL UNIQUE REFERENCES users (id),
    balance    NUMERIC(10, 2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE wallet_transactions (
    id           UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id    UUID          NOT NULL REFERENCES wallets (id),
    amount       NUMERIC(10, 2) NOT NULL,
    type         VARCHAR(20)   NOT NULL,
    description  VARCHAR(500),
    reference_id UUID,
    created_at   TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_wallet_transactions_wallet_id ON wallet_transactions (wallet_id);
