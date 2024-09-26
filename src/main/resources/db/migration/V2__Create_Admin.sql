-- Миграция Flyway для создания администратора

INSERT INTO usr ( username, password, email, active)
VALUES ('admin', '$2a$10$V/Vg3THOVu1KW241lyKcVus3BwHaoQyoAJK4ovb57B8duWbFMteNG', 'whitecrowit@mail.ru', true);

-- Привязываем роль ADMIN к администратору, используя сгенерированный id
INSERT INTO user_role (user_id, roles)
VALUES ((SELECT id FROM usr WHERE username = 'admin'), 'ADMIN');
