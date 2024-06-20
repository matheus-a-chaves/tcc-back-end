CREATE TABLE formato (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO formato
(id, nome)
VALUES(1, 'Pontos Corridos');

INSERT INTO formato
(id, nome)
VALUES(2, 'Eliminatória Simples');

INSERT INTO formato
(id, nome)
VALUES(4, 'Fase de Grupos + Eliminatória Simples');

INSERT INTO formato
(id, nome)
VALUES(6, 'Fase de Grupos + Pontos Corridos');
