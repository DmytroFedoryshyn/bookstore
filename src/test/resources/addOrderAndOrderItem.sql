insert into orders (id, user_id, status, total, order_date, shipping_address)
       values (1, 2, 1, 35, '2000-01-01', 'address');
insert into order_items (id, order_id, book_id, quantity, price)
       values (1, 1, 1, 2, 35);
insert into orders_items (order_id, order_item_id)
        values (1, 1);
