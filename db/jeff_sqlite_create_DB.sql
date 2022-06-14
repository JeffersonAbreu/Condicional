--
-- File generated with SQLiteStudio v3.3.3 on sex mai 27 19:40:20 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

DROP TABLE IF EXISTS ITENS_CONDICIONAL;
DROP TABLE IF EXISTS CONDICIONAL;
DROP TABLE IF EXISTS CLIENTE;
DROP TABLE IF EXISTS ROUPA;
DROP TABLE IF EXISTS ATENDENTE;

-- Table: ATENDENTE
CREATE TABLE ATENDENTE (
    id_atendente INTEGER PRIMARY KEY AUTOINCREMENT,
    nome         VARCHAR NOT NULL,
    login        VARCHAR NOT NULL,
    senha        VARCHAR NOT NULL
);

-- Table: CLIENTE
CREATE TABLE CLIENTE (
    id_cliente      INTEGER  PRIMARY KEY AUTOINCREMENT
                             NOT NULL,
    nome            VARCHAR  NOT NULL,
    data_nascimento DATE,
    cpf             VARCHAR,
    email           VARCHAR,
    telefone        VARCHAR,
    celular         VARCHAR,
    logradouro      VARCHAR,
    bairro          VARCHAR,
    cidade          VARCHAR,
    uf              VARCHAR,
    cep             VARCHAR,
    limite          FLOAT    NOT NULL
);

-- Table: ROUPA
CREATE TABLE ROUPA (
    id_roupa           INTEGER PRIMARY KEY AUTOINCREMENT
                               NOT NULL,
    nome               VARCHAR NOT NULL,
    valor              FLOAT   NOT NULL,
    qtd                INTEGER NOT NULL,
    qtd_em_condicional INTEGER
);

-- Table: CONDICIONAL
CREATE TABLE CONDICIONAL (
    id_condicional INTEGER  PRIMARY KEY AUTOINCREMENT
                            NOT NULL,
    id_cliente     INTEGER  NOT NULL
                            REFERENCES CLIENTE (id_cliente),
    id_atendente   INTEGER  REFERENCES ATENDENTE (id_atendente) 
                            NOT NULL,
    data_hora      DATETIME NOT NULL,
    valor          FLOAT    NOT NULL,
    qtd            INTEGER  NOT NULL,
    ativo          INTEGER  NOT NULL
);

-- Table: ITENS_CONDICIONAL
CREATE TABLE ITENS_CONDICIONAL (
    id_condicional INTEGER REFERENCES CONDICIONAL (id_condicional) 
                           NOT NULL,
    id_roupa       INTEGER REFERENCES ROUPA (id_roupa) 
                           NOT NULL,
    valor_uni      FLOAT   NOT NULL,
    qtd            INTEGER NOT NULL,
    valor_total    FLOAT   NOT NULL
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
