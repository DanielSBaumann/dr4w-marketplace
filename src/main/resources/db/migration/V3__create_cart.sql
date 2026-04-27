CREATE TABLE carts (
    id         UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID      NOT NULL UNIQUE REFERENCES users (id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE cart_items (
    id             UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id        UUID          NOT NULL REFERENCES carts (id) ON DELETE CASCADE,
    product_id     UUID          NOT NULL REFERENCES products (id),
    quantity       INTEGER       NOT NULL CHECK (quantity > 0),
    price_snapshot NUMERIC(10, 2) NOT NULL,
    added_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    UNIQUE (cart_id, product_id)
);

CREATE INDEX idx_cart_items_cart_id ON cart_items (cart_id);
