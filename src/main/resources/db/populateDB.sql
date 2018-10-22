DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
    ('User', 'user@yandex.ru', 'password'),
    ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
    ('ROLE_USER', 100000),
    ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
    (100000, '2018-10-20 07:00:00', 'Breakfast', 600),
    (100000, '2018-10-20 12:00:00', 'Lunch', 1000),
    (100000, '2018-10-21 07:00:00', 'Breakfast', 700),
    (100000, '2018-10-21 12:00:00', 'Lunch', 800),
    (100001, '2018-10-21 13:30:00', 'Lunch', 1400),
    (100001, '2018-10-21 19:10:00', 'Dinner', 300)
