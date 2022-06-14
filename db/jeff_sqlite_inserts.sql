--
-- File generated with SQLiteStudio v3.3.3 on dom jun 12 23:31:38 2022
--
-- Text encoding used: UTF-8
--
INSERT INTO ATENDENTE 
(nome,login,senha)
VALUES 
('Ana','ana','123'),
('Beatriz','bia','123'),
('Gerlane','Gee','259'),
('Francielle','Fran','137'),
('Luciana','Luh','301');


-- Table: CLIENTE
INSERT INTO CLIENTE 
(nome,data_nascimento,cpf,email,telefone,celular,logradouro,bairro,cidade,uf,cep,limite)
VALUES 
('Luciana V. Moreira',32516305200000,'11111111111','lu@moreira.com','','999900000','Rua Ceu Claro','Alto','Vitória','ES','29000000',1000.0),
('Juliana Garcia',32516305200000,'11111111111','lu@moreira.com','','9999900000','Rua Ceu Claro','Alto','Vitória','ES','29000000',100.0),
('Helen',32516305200000,'22222222233','hele@gmail.com','','99999999','Rua Ceu Claro','Amarela','Castanhal','RJ','34.200-300',200.0),
('Danielly',10061978,'052.647.985-25','dam@hotmail.com','','99969-3340','Colinas','Amarelo','Cachoeiro de Itapemirim','ES','29.300-000',2000.0),
('Genoveva',25101970,'555.879.714-56','geno@outlook.com','','99909-1035','Rua das flores','Aeroport','Cachoeiro de Itapemirim','ES','29.300-000',1000.0),
('Joana',9081974,'235.999.787.96','joana@hotmail.com','','99999-7671','Rua Hum','Gilb. Machado','Cachoeiro de Itapemirim','ES','29.300-000',1350.0),
('Lourdes',5031970,'555.869.125-32','lolo@gmail.com','','99909-1111','Ibitirama','BNH','Cachoeiro de Itapemirim','ES','29.300-000',2500.0),
('Maria Vieira',30121980,'093.172.917-35','mv@gmail.com','','99955-3616','Colatina','Zumbi','Cachoeiro de Itapemirim','ES','29.302-160',800.0),
('Simone',28021960,'478.333.888.-86','smv@outlook.com','','99922-8539','Rua da Felicidade','IBC','Cachoeiro de Itapemirim','ES','29.300-000',800.0),
('Sirlene',1011975,'147.985.871.96','sslevi@gmail.com','','99923-3206','Rua Projetada','Vilage','Cachoeiro de Itapemirim','ES','29.300-000',5000.0),
('Virginia',25121990,'254.874.896-54','vi@gmail.com','','99999-7671','Altoe','Baiminas','Cachoeiro de Itapemirim','ES','29.300-000',3300.0);


-- Table: ROUPA

INSERT INTO ROUPA 
(nome,valor,qtd,qtd_em_condicional)
VALUES 
('Jaqueta de Couro - Preta','300,00',3,1),
('Jaqueta Jeans - Azul','199,99',5,0),
('Vestido Longo - Verde','187,50',10,0),
('Vestito Curto - Branco',99.0,7,2),
('Vestido Curto - Preto',129.0,3,1),
('Calca Jeans - GG - Azul',99.0,4,1),
('Calca Jeans - G - Azul','99,99',4,0),
('Calca Jeans - M - Azul',99.0,4,1),
('Calca Jeans - P - Azul','99,99',4,1),
('Blusinha de Alca Basica - Branca','59,99',3,1),
('Vestido jeans, P, preto','399,90',5,0),
('Vestido de malha GG branco','109,90',6,0),
('Vestido verde, G, off','99,90',2,0),
('Chamise, uva, tam. Único','179,90',3,0),
('Cachecol','39,90',1,0),
('Touca','29,90',2,0),
('Saia de couro, P','189,90',5,0),
('Saia jeans, 38','119,90',4,0),
('Tricot laranja, tam. Único','99,90',2,0),
('Leggin, P, lycra','49,90',3,0),
('Blusa onça, M, malha','79,90',6,0),
('Blusa princesa, EX, rosa','79,90',5,0),
('Conj. Moleton, P, cinza','279,90',4,0),
('Cinto onça','49,90',5,0),
('Cinto zebra','49,90',5,0),
('Blazer, M, bege','299,00',7,0),
('Short linho, 38, bege',139.0,4,1),
('Terninho, M , preto',329.0,5,3),
('Cardigan, uva, tamanho único',219.0,4,4),
('Tricot laranja, tamanho único',89.0,3,1),
('Camiseta, alça,G,branca','19,90',1,0),
('Camisa social, listrada,GG',129.0,1,1),
('Cropped, renda, P, branco','29,90',2,0),
('Bolsa, preta, sintética',89.0,4,2),
('Bolsa, marron, couro ','899,90',5,0),
('Short, cinza, M',39.0,1,1);

-- Table: CONDICIONAL
INSERT INTO CONDICIONAL 
(id_cliente,id_atendente,data_hora,valor,qtd,ativo)
VALUES 
(2,5,1546308000000,99.0,1,1),
(8,3,1559358000000,198.0,2,1),
(10,2,1585710000000,1870.0,10,1),
(7,4,1590980400000,89.0,1,1),
(10,3,1619838000000,219.0,1,1),
(11,4,1641006000000,129.0,1,1),
(6,3,1646103600000,219.0,1,1),
(6,2,1655002800000,139.0,1,1);


-- Table: ITENS_CONDICIONAL
INSERT INTO ITENS_CONDICIONAL 
(id_condicional,id_roupa,valor_uni,qtd,valor_total)
VALUES 
(8,27,139.0,1,139.0),
(1,4,99.0,1,99.0),
(2,4,99.0,1,99.0),
(2,8,99.0,1,99.0),
(4,34,89.0,1,89.0),
(3,28,329.0,3,987.0),
(3,29,219.0,2,438.0),
(3,36,39.0,1,39.0),
(3,34,89.0,1,89.0),
(3,30,89.0,1,89.0),
(3,5,129.0,1,129.0),
(3,6,99.0,1,99.0),
(5,29,219.0,1,219.0),
(6,32,129.0,1,129.0),
(7,29,219.0,1,219.0);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
