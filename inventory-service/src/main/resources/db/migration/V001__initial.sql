CREATE TABLE INVENTORY(
    id BIGSERIAL PRIMARY KEY,
    sku_code VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);

CREATE SEQUENCE if not exists inventory_id_seq
    START WITH 1
    INCREMENT BY 1;

INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU001', 10);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU002', 0);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU003', 5);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU004', 20);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU005', 15);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU006', 0);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU007', 8);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU008', 12);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU009', 0);
INSERT INTO INVENTORY (sku_code, quantity) VALUES ('SKU010', 25);