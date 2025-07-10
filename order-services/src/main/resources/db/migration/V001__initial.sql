CREATE TABLE ORDERS
(
    id                 BIGSERIAL PRIMARY KEY,
    order_number       VARCHAR(255) NOT NULL,
    order_date         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    order_updated_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE if not exists order_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE ORDER_LINE_ITEMS
(
    id       BIGSERIAL PRIMARY KEY,
    sku_code VARCHAR(255)   NOT NULL,
    price    NUMERIC(10, 2) NOT NULL,
    quantity INT            NOT NULL,
    order_id BIGINT        NOT NULL,
    FOREIGN KEY (order_id) REFERENCES ORDERS(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE if not exists order_line_items_id_seq
    START WITH 1
    INCREMENT BY 1;