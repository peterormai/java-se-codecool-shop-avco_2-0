DROP TABLE IF EXISTS lineItem;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS productCategories;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS orders;

CREATE TABLE products (
    id serial NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE,
    price numeric CHECK (price > 0),
    currency varchar(255) NOT NULL,
    picture varchar(255) NOT NULL,
    product_category_id int NOT NULL,
    supplier_id int NOT NULL
);


CREATE TABLE productCategories (
    id serial NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE,
    department varchar(255) default 'No department',
    description varchar(255) default 'No description'
);

CREATE TABLE orders (
    id serial NOT NULL primary key,
    user_id int NOT NULL default 1,
    status varchar(255) NOT NULL CHECK (status IN ('New','Done','Checked','Payed') )default 'New',
    OrderDate char(16) default to_char(LOCALTIMESTAMP, 'YYYY-MM-DD HH24:MI')

);

CREATE TABLE lineItem (
    id serial NOT NULL primary key,
    product_id int NOT NULL,
    quantity int NOT NULL default 1,
    order_id int NOT NULL
);


CREATE TABLE suppliers (
    id serial NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE,
    description varchar(255) default 'No description'
);

ALTER TABLE ONLY products
ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES productCategories(id) ON DELETE CASCADE;

ALTER TABLE ONLY products
ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE;

ALTER TABLE ONLY lineItem
ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE;

INSERT INTO orders default values;

