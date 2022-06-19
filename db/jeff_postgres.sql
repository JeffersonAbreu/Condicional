--
-- DABASE: comodato
--
-- UserName: postgres
-- Password: postgres
--



CREATE TABLE ATENDENTE (
    id_atendente INTEGER NOT NULL GENERATED ALWAYS 
       AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome         VARCHAR(45) NOT NULL,
    login        VARCHAR(45) NOT NULL,
    senha        VARCHAR(20) NOT NULL,

    CONSTRAINT pk_atendente
      PRIMARY KEY(id_atendente)
);
CREATE TABLE CLIENTE (
    id_cliente      INTEGER  NOT NULL GENERATED ALWAYS 
       AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome            VARCHAR(45)  NOT NULL,
    data_nascimento DATE,
    cpf             VARCHAR(45),
    email           VARCHAR(45),
    telefone        VARCHAR(45),
    celular         VARCHAR(45),
    logradouro      VARCHAR(45),
    bairro          VARCHAR(45),
    cidade          VARCHAR(45),
    uf              VARCHAR(45),
    cep             VARCHAR(45),
    limite          FLOAT    NOT NULL,

    CONSTRAINT pk_cliente
      PRIMARY KEY(id_cliente)
);

CREATE TABLE ROUPA (
    id_roupa           INTEGER NOT NULL GENERATED ALWAYS 
       AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome               VARCHAR(45) NOT NULL,
    valor              FLOAT   NOT NULL,
    qtd                INTEGER NOT NULL,
    qtd_em_condicional INTEGER,

    CONSTRAINT pk_roupa
      PRIMARY KEY(id_roupa)
);


CREATE TABLE CONDICIONAL (
    id_condicional INTEGER  NOT NULL GENERATED ALWAYS 
       AS IDENTITY (START WITH 1 INCREMENT BY 1),
    id_cliente     INTEGER  NOT NULL,
    id_atendente   INTEGER  NOT NULL,
    data_hora      TIMESTAMP NOT NULL,
    valor          FLOAT    NOT NULL,
    qtd            INTEGER  NOT NULL,
    ativo          INTEGER  NOT NULL,

    CONSTRAINT pk_condicional
      PRIMARY KEY(id_condicional),

    CONSTRAINT fk_cliente
      FOREIGN KEY (id_cliente)
      REFERENCES CLIENTE(id_cliente),

    CONSTRAINT fk_atendente
      FOREIGN KEY (id_atendente)
      REFERENCES ATENDENTE(id_atendente)  
);

CREATE TABLE ITENS_CONDICIONAL (
    id_condicional INTEGER NOT NULL,
    id_roupa       INTEGER NOT NULL,
    valor_uni      FLOAT   NOT NULL,
    qtd            INTEGER NOT NULL,
    valor_total    FLOAT   NOT NULL,

    CONSTRAINT fk_condicional
      FOREIGN KEY (id_condicional)
      REFERENCES CONDICIONAL(id_condicional),

    CONSTRAINT fk_roupa
      FOREIGN KEY (id_roupa)
      REFERENCES ROUPA(id_roupa)  
);

INSERT INTO ATENDENTE  (nome,login,senha)
VALUES 
('Ana','ana','123'),
('Beatriz','bia','123'),
('Gerlane','Gee','259'),
('Francielle','Fran','137'),
('Luciana','Luh','301');

INSERT INTO CLIENTE (nome,data_nascimento,cpf,email,telefone,celular,logradouro,bairro,cidade,uf,cep,limite)
VALUES 
('Luciana V. Moreira','1980-06-14','11111111111','lu@moreira.com','','999900000','Rua Ceu Claro','Alto','Vitória','ES','29000000',1000.0),
('Juliana Garcia','1980-06-14','11111111111','lu@moreira.com','','9999900000','Rua Ceu Claro','Alto','Vitória','ES','29000000',100.0),
('Helen','1980-06-14','22222222233','hele@gmail.com','','99999999','Rua Ceu Claro','Amarela','Castanhal','RJ','34.200-300',200.0),
('Danielly','1980-06-14','052.647.985-25','dam@hotmail.com','','99969-3340','Colinas','Amarelo','Cachoeiro de Itapemirim','ES','29.300-000',2000.0),
('Genoveva','1980-06-14','555.879.714-56','geno@outlook.com','','99909-1035','Rua das flores','Aeroport','Cachoeiro de Itapemirim','ES','29.300-000',1000.0),
('Joana','1980-06-14','235.999.787.96','joana@hotmail.com','','99999-7671','Rua Hum','Gilb. Machado','Cachoeiro de Itapemirim','ES','29.300-000',1350.0),
('Lourdes','1980-06-14','555.869.125-32','lolo@gmail.com','','99909-1111','Ibitirama','BNH','Cachoeiro de Itapemirim','ES','29.300-000',2500.0),
('Maria Vieira','1980-06-14','093.172.917-35','mv@gmail.com','','99955-3616','Colatina','Zumbi','Cachoeiro de Itapemirim','ES','29.302-160',800.0),
('Simone','1980-06-14','478.333.888.-86','smv@outlook.com','','99922-8539','Rua da Felicidade','IBC','Cachoeiro de Itapemirim','ES','29.300-000',800.0),
('Sirlene','1980-06-14','147.985.871.96','sslevi@gmail.com','','99923-3206','Rua Projetada','Vilage','Cachoeiro de Itapemirim','ES','29.300-000',5000.0),
('Virginia','1980-06-14','254.874.896-54','vi@gmail.com','','99999-7671','Altoe','Baiminas','Cachoeiro de Itapemirim','ES','29.300-000',3300.0);

INSERT INTO ROUPA (nome,valor,qtd,qtd_em_condicional)
VALUES 
('Jaqueta de Couro - Preta',300.00,3,1),
('Jaqueta Jeans - Azul',199.99,5,0),
('Vestido Longo - Verde',187.50,10,0),
('Vestito Curto - Branco',99.0,7,2),
('Vestido Curto - Preto',129.0,3,1),
('Calca Jeans - GG - Azul',99.0,4,1),
('Calca Jeans - G - Azul',99.99,4,0),
('Calca Jeans - M - Azul',99.0,4,1),
('Calca Jeans - P - Azul',99.99,4,1),
('Blusinha de Alca Basica - Branca',59.99,3,1),
('Vestido jeans, P, preto',399.90,5,0),
('Vestido de malha GG branco',109.90,6,0),
('Vestido verde, G, off',99.90,2,0),
('Chamise, uva, tam. Único',179.90,3,0),
('Cachecol',39.90,1,0),
('Touca',29.90,2,0),
('Saia de couro, P',189.90,5,0),
('Saia jeans, 38',119.90,4,0),
('Tricot laranja, tam. Único',99.90,2,0),
('Leggin, P, lycra',49.90,3,0),
('Blusa onça, M, malha',79.90,6,0),
('Blusa princesa, EX, rosa',79.90,5,0),
('Conj. Moleton, P, cinza',279.90,4,0),
('Cinto onça',49.90,5,0),
('Cinto zebra',49.90,5,0),
('Blazer, M, bege',299.00,7,0),
('Short linho, 38, bege',139.0,4,1),
('Terninho, M , preto',329.0,5,3),
('Cardigan, uva, tamanho único',219.0,4,4),
('Tricot laranja, tamanho único',89.0,3,1),
('Camiseta, alça,G,branca',19.90,1,0),
('Camisa social, listrada,GG',129.0,1,1),
('Cropped, renda, P, branco',29.90,2,0),
('Bolsa, preta, sintética',89.0,4,2),
('Bolsa, marron, couro ',899.90,5,0),
('Short, cinza, M',39.0,1,1);


INSERT INTO CONDICIONAL (id_cliente,id_atendente,data_hora,valor,qtd,ativo)
VALUES 
(2,5,'2021-04-01',99.0,1,1),
(8,3,'2021-06-28',198.0,2,1),
(10,2,'2021-08-04',1870.0,10,1),
(7,4,'2021-10-07',89.0,1,1),
(10,3,'2021-12-14',219.0,1,1),
(11,4,'2022-02-13',129.0,1,1),
(6,3,'2022-04-11',219.0,1,1),
(6,2,'2022-06-14',139.0,1,1);

INSERT INTO ITENS_CONDICIONAL (id_condicional,id_roupa,valor_uni,qtd,valor_total)
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