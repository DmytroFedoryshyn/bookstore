insert into users (id, email, password, first_name, last_name)
VALUES (2, 'user@example.com', '$2a$10$O4lGmESaPLGoeUzJnK2/4ul5rGH01NBESF03vSumBbNxHetuNdTo6',
        'John', 'Smith');

insert into user_roles (user_id, role_id) values (2, 2);
