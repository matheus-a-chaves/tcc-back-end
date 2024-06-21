CREATE TABLE modalidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO modalidade
(id, nome)
VALUES(1, 'Futebol');

INSERT INTO modalidade
(id, nome)
VALUES(2, 'Futsal');

INSERT INTO modalidade
(id, nome)
VALUES(3, 'Fut7');

INSERT INTO modalidade
(id, nome)
VALUES(4, 'Basquete');

INSERT INTO modalidade
(id, nome)
VALUES(5, 'Volei');

INSERT INTO modalidade
(id, nome)
VALUES(6, 'Handebol');