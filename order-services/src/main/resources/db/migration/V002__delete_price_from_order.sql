ALTER TABLE orders
    DROP IF EXISTS price;

ALTER TABLE order_line_items
    DROP IF EXISTS price;