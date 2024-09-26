-- Создание последовательности usr_seq
CREATE SEQUENCE usr_seq
    START WITH 1
    INCREMENT BY 1;


-- Создание таблицы usr
CREATE TABLE usr (
                     id BIGSERIAL PRIMARY KEY,
                     active BOOLEAN NOT NULL,
                     date_of_birth DATE,
                     email VARCHAR(255) NOT NULL,
                     password VARCHAR(255) NOT NULL,
                     phone_number VARCHAR(255),
                     username VARCHAR(255) NOT NULL
);



-- Создание таблицы user_role
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           roles VARCHAR(255)
);

-- Добавление внешнего ключа
ALTER TABLE user_role
    ADD CONSTRAINT FK_user_role_usr
        FOREIGN KEY (user_id)
            REFERENCES usr (id);
