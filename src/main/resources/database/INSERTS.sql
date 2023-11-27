INSERT INTO public.endereco
(id, bairro, cep, cidade, complemento, numero, rua)
VALUES(1, 'Jardim das Américas', '81520-260', 'Curitiba', 'na faculdade', '1225', 'R. Dr. Alcides Vieira Arcoverde');
INSERT INTO public.endereco
(id, bairro, cep, cidade, complemento, numero, rua)
VALUES(2, 'Água Verde', '80250-070', 'Curitiba', 'Em frente a praça', '1260', 'R. Buenos Aires');
INSERT INTO public.endereco
(id, bairro, cep, cidade, complemento, numero, rua)
VALUES(3, 'Novo Mundo', '81050-450', 'Curitiba', NULL, '1000', 'Rua Carolina Castelli');
INSERT INTO public.endereco
(id, bairro, cep, cidade, complemento, numero, rua)
VALUES(4, 'Portão', '81070-000', 'Curitiba', NULL, '3173', 'R. João Bettega');

-----------------------------------------------------------------------------------

INSERT INTO public.usuario
(id, data_nascimento, email, logo, nome, telefone, tipo_usuario, fk_endereco)
VALUES(1, '1990-01-14', 'kraken@email.com', '16869', 'Kraken - UFPR', '(41)987654321', 'Atletica', 1);
INSERT INTO public.usuario
(id, data_nascimento, email, logo, nome, telefone, tipo_usuario, fk_endereco)
VALUES(2, '1985-05-19', 'athleticopr@email.com', '16871', 'Athletico-PR', '(41)985654520', 'Atletica', 2);

-----------------------------------------------------------------------------------
INSERT INTO public.modalidade
(id, modalidade)
VALUES(1, 'Futebol');
INSERT INTO public.modalidade
(id, modalidade)
VALUES(2, 'Basquete');


-----------------------------------------------------------------------------------

INSERT INTO public."local"
(id, descricao, logo, nome, telefone, valor, fk_endereco)
VALUES(1, 'Descrição', 16984, 'Top Sports Centro Esportivo', '(41)3346-1117', 120.0, 3);
INSERT INTO public."local"
(id, descricao, logo, nome, telefone, valor, fk_endereco)
VALUES(2, 'Descrição', 16985, 'JB Esportes', '(41)3346-1117', 150.0, 4);

-----------------------------------------------------------------------------------

INSERT INTO public.equipe
(id, logo, nome, fk_modalidade, fk_usuario)
VALUES(1, 16872, 'Atletico-PR', 1, 3);

