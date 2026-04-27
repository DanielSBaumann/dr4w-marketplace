CREATE TABLE orders (
    id           UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    buyer_id     UUID          NOT NULL REFERENCES users (id),
    status       VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    total_amount NUMERIC(10, 2) NOT NULL,
    created_at   TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id          UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id    UUID          NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id  UUID          NOT NULL REFERENCES products (id),
    vendor_id   UUID          NOT NULL REFERENCES users (id),
    quantity    INTEGER       NOT NULL,
    unit_price  NUMERIC(10, 2) NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE payments (
    id                UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id          UUID          NOT NULL REFERENCES orders (id),
    status            VARCHAR(20)   NOT NULL,
    payment_method    VARCHAR(50)   NOT NULL,
    amount            NUMERIC(10, 2) NOT NULL,
    gateway_reference VARCHAR(200),
    created_at        TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_orders_buyer_id ON orders (buyer_id);
CREATE INDEX idx_orders_status   ON orders (status);
CREATE INDEX idx_order_items_order_id   ON order_items (order_id);
CREATE INDEX idx_order_items_vendor_id  ON order_items (vendor_id);
