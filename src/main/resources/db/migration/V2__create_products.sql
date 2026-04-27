CREATE TABLE products (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vendor_id           UUID         NOT NULL REFERENCES users (id),
    name                VARCHAR(200) NOT NULL,
    brand               VARCHAR(200) NOT NULL,
    description         TEXT         NOT NULL,
    category            VARCHAR(50)  NOT NULL,
    price               NUMERIC(10, 2) NOT NULL,
    discount_percentage INTEGER      NOT NULL DEFAULT 0,
    final_price         NUMERIC(10, 2) NOT NULL,
    stock_quantity      INTEGER      NOT NULL DEFAULT 0,
    sold_quantity       INTEGER      NOT NULL DEFAULT 0,
    image_url_1         VARCHAR(500),
    image_url_2         VARCHAR(500),
    image_url_3         VARCHAR(500),
    image_url_4         VARCHAR(500),
    active              BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_vendor_id ON products (vendor_id);
CREATE INDEX idx_products_category  ON products (category);
CREATE INDEX idx_products_active    ON products (active);
CREATE INDEX idx_products_name      ON products USING gin (to_tsvector('portuguese', name));
