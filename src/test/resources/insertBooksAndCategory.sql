INSERT INTO categories (id, name, description)
VALUES (1, 'name', 'description');
INSERT INTO categories (id, name, description)
VALUES (2, 'name2', 'description2');

INSERT INTO books (id, isbn, author, title, cover_image, description, price)
VALUES (1, '1234', '123', '123', '123', '123', 10.00);

INSERT INTO books (id, isbn, author, title, cover_image, description, price)
VALUES (2, '123', '123', '123', '123', '123', 10.00);

INSERT INTO books (id, isbn, author, title, cover_image, description, price)
VALUES (3, '1235', '123', '123', '123', '123', 10.00);

INSERT INTO books_categories (book_id, category_id)
VALUES (1, 1);

INSERT INTO books_categories (book_id, category_id)
VALUES (2, 2);

INSERT INTO books_categories (book_id, category_id)
VALUES (3, 1);

INSERT INTO books_categories (book_id, category_id)
VALUES (3, 2);

