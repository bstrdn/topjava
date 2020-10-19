DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-10-17 10:00:00', 'Завтрак', '1000'),
       (100000, '2020-10-17 20:00:00', 'Ужин', '1500'),
       (100001, '2020-10-17 11:00:00', 'Завтрак', '1500'),
       (100001, '2020-10-17 19:00:00', 'Ужин', '2500');