--
-- File generated with SQLiteStudio v3.3.3 on dom jun 12 23:31:38 2022
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: ATENDENTE
DROP TABLE IF EXISTS ATENDENTE;

CREATE TABLE ATENDENTE (
    id_atendente INTEGER PRIMARY KEY AUTOINCREMENT,
    nome         VARCHAR NOT NULL,
    login        VARCHAR NOT NULL,
    senha        VARCHAR NOT NULL
);

INSERT INTO ATENDENTE (
                          id_atendente,
                          nome,
                          login,
                          senha
                      )
                      VALUES (
                          1,
                          'Ana',
                          'ana',
                          '123'
                      );

INSERT INTO ATENDENTE (
                          id_atendente,
                          nome,
                          login,
                          senha
                      )
                      VALUES (
                          2,
                          'Beatriz',
                          'bia',
                          '123'
                      );

INSERT INTO ATENDENTE (
                          id_atendente,
                          nome,
                          login,
                          senha
                      )
                      VALUES (
                          3,
                          'Gerlane',
                          'Gee',
                          '259'
                      );

INSERT INTO ATENDENTE (
                          id_atendente,
                          nome,
                          login,
                          senha
                      )
                      VALUES (
                          4,
                          'Francielle',
                          'Fran',
                          '137'
                      );

INSERT INTO ATENDENTE (
                          id_atendente,
                          nome,
                          login,
                          senha
                      )
                      VALUES (
                          5,
                          'Luciana',
                          'Luh',
                          '301'
                      );


-- Table: CLIENTE
DROP TABLE IF EXISTS CLIENTE;

CREATE TABLE CLIENTE (
    id_cliente      INTEGER PRIMARY KEY AUTOINCREMENT
                            NOT NULL,
    nome            VARCHAR NOT NULL,
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
    limite          FLOAT   NOT NULL
);

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        1,
                        'Luciana V. Moreira',
                        32516305200000,
                        '11111111111',
                        'lu@moreira.com',
                        '',
                        '999900000',
                        'Rua Ceu Claro',
                        'Alto',
                        'Vitória',
                        'ES',
                        '29000000',
                        1000.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        2,
                        'Juliana Garcia',
                        32516305200000,
                        '11111111111',
                        'lu@moreira.com',
                        '',
                        '9999900000',
                        'Rua Ceu Claro',
                        'Alto',
                        'Vitória',
                        'ES',
                        '29000000',
                        100.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        3,
                        'Helen',
                        32516305200000,
                        '22222222233',
                        'hele@gmail.com',
                        '',
                        '99999999',
                        'Rua Ceu Claro',
                        'Amarela',
                        'Castanhal',
                        'RJ',
                        '34.200-300',
                        200.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        4,
                        'Danielly',
                        10061978,
                        '052.647.985-25',
                        'dam@hotmail.com',
                        '',
                        '99969-3340',
                        'Colinas',
                        'Amarelo',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        2000.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        5,
                        'Genoveva',
                        25101970,
                        '555.879.714-56',
                        'geno@outlook.com',
                        '',
                        '99909-1035',
                        'Rua das flores',
                        'Aeroport',
                        'Cachoeiro de Itapemirim',
                        'Es',
                        '29.300-000',
                        1000.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        6,
                        'Joana',
                        9081974,
                        '235.999.787.96',
                        'joana@hotmail.com',
                        '',
                        '99999-7671',
                        'Rua Hum',
                        'Gilb. Machado',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        1350.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        7,
                        'Lourdes',
                        5031970,
                        '555.869.125-32',
                        'lolo@gmail.com',
                        '',
                        '99909-1111',
                        'Ibitirama',
                        'BNH',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        2500.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        8,
                        'Maria Vieira',
                        30121980,
                        '093.172.917-35',
                        'mv@gmail.com',
                        '',
                        '99955-3616',
                        'Colatina',
                        'Zumbi',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.302-160',
                        800.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        9,
                        'Simone',
                        28021960,
                        '478.333.888.-86',
                        'smv@outlook.com',
                        '',
                        '99922-8539',
                        'Rua da Felicidade',
                        'IBC',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        800.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        10,
                        'Sirlene',
                        1011975,
                        '147.985.871.96',
                        'sslevi@gmail.com',
                        '',
                        '99923-3206',
                        'Rua Projetada',
                        'Vilage',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        5000.0
                    );

INSERT INTO CLIENTE (
                        id_cliente,
                        nome,
                        data_nascimento,
                        cpf,
                        email,
                        telefone,
                        celular,
                        logradouro,
                        bairro,
                        cidade,
                        uf,
                        cep,
                        limite
                    )
                    VALUES (
                        11,
                        'Virginia',
                        25121990,
                        '254.874.896-54',
                        'vi@gmail.com',
                        '',
                        '99999-7671',
                        'Altoe',
                        'Baiminas',
                        'Cachoeiro de Itapemirim',
                        'ES',
                        '29.300-000',
                        3300.0
                    );


-- Table: CONDICIONAL
DROP TABLE IF EXISTS CONDICIONAL;

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

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            1,
                            2,
                            5,
                            1546308000000,
                            99.0,
                            1,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            2,
                            8,
                            3,
                            1559358000000,
                            198.0,
                            2,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            3,
                            10,
                            2,
                            1585710000000,
                            1870.0,
                            10,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            4,
                            7,
                            4,
                            1590980400000,
                            89.0,
                            1,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            5,
                            10,
                            3,
                            1619838000000,
                            219.0,
                            1,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            6,
                            11,
                            4,
                            1641006000000,
                            129.0,
                            1,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            7,
                            6,
                            3,
                            1646103600000,
                            219.0,
                            1,
                            1
                        );

INSERT INTO CONDICIONAL (
                            id_condicional,
                            id_cliente,
                            id_atendente,
                            data_hora,
                            valor,
                            qtd,
                            ativo
                        )
                        VALUES (
                            8,
                            6,
                            2,
                            1655002800000,
                            139.0,
                            1,
                            1
                        );


-- Table: ITENS_CONDICIONAL
DROP TABLE IF EXISTS ITENS_CONDICIONAL;

CREATE TABLE ITENS_CONDICIONAL (
    id_condicional INTEGER REFERENCES CONDICIONAL (id_condicional) 
                           NOT NULL,
    id_roupa       INTEGER REFERENCES ROUPA (id_roupa) 
                           NOT NULL,
    valor_uni      FLOAT   NOT NULL,
    qtd            INTEGER NOT NULL,
    valor_total    FLOAT   NOT NULL
);

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  8,
                                  27,
                                  139.0,
                                  1,
                                  139.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  1,
                                  4,
                                  99.0,
                                  1,
                                  99.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  2,
                                  4,
                                  99.0,
                                  1,
                                  99.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  2,
                                  8,
                                  99.0,
                                  1,
                                  99.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  4,
                                  34,
                                  89.0,
                                  1,
                                  89.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  28,
                                  329.0,
                                  3,
                                  987.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  29,
                                  219.0,
                                  2,
                                  438.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  36,
                                  39.0,
                                  1,
                                  39.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  34,
                                  89.0,
                                  1,
                                  89.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  30,
                                  89.0,
                                  1,
                                  89.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  5,
                                  129.0,
                                  1,
                                  129.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  3,
                                  6,
                                  99.0,
                                  1,
                                  99.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  5,
                                  29,
                                  219.0,
                                  1,
                                  219.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  6,
                                  32,
                                  129.0,
                                  1,
                                  129.0
                              );

INSERT INTO ITENS_CONDICIONAL (
                                  id_condicional,
                                  id_roupa,
                                  valor_uni,
                                  qtd,
                                  valor_total
                              )
                              VALUES (
                                  7,
                                  29,
                                  219.0,
                                  1,
                                  219.0
                              );


-- Table: ROUPA
DROP TABLE IF EXISTS ROUPA;

CREATE TABLE ROUPA (
    id_roupa           INTEGER PRIMARY KEY AUTOINCREMENT
                               NOT NULL,
    nome               VARCHAR NOT NULL,
    valor              FLOAT   NOT NULL,
    qtd                INTEGER NOT NULL,
    qtd_em_condicional INTEGER
);

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      1,
                      'Jaqueta de Couro - Preta',
                      '300,00',
                      3,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      2,
                      'Jaqueta Jeans - Azul',
                      '199,99',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      3,
                      'Vestido Longo - Verde',
                      '187,50',
                      10,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      4,
                      'Vestito Curto - Branco',
                      99.0,
                      7,
                      2
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      5,
                      'Vestido Curto - Preto',
                      129.0,
                      3,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      6,
                      'Calca Jeans - GG - Azul',
                      99.0,
                      4,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      7,
                      'Calca Jeans - G - Azul',
                      '99,99',
                      4,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      8,
                      'Calca Jeans - M - Azul',
                      99.0,
                      4,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      9,
                      'Calca Jeans - P - Azul',
                      '99,99',
                      4,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      10,
                      'Blusinha de Alca Basica - Branca',
                      '59,99',
                      3,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      11,
                      'Vestido jeans, P, preto',
                      '399,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      12,
                      'Vestido de malha GG branco',
                      '109,90',
                      6,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      13,
                      'Vestido verde, G, off',
                      '99,90',
                      2,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      14,
                      'Chamise, uva, tam. Único',
                      '179,90',
                      3,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      15,
                      'Cachecol',
                      '39,90',
                      1,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      16,
                      'Touca',
                      '29,90',
                      2,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      17,
                      'Saia de couro, P',
                      '189,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      18,
                      'Saia jeans, 38',
                      '119,90',
                      4,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      19,
                      'Tricot laranja, tam. Único',
                      '99,90',
                      2,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      20,
                      'Leggin, P, lycra',
                      '49,90',
                      3,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      21,
                      'Blusa onça, M, malha',
                      '79,90',
                      6,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      22,
                      'Blusa princesa, EX, rosa',
                      '79,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      23,
                      'Conj. Moleton, P, cinza',
                      '279,90',
                      4,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      24,
                      'Cinto onça',
                      '49,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      25,
                      'Cinto zebra',
                      '49,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      26,
                      'Blazer, M, bege',
                      '299,00',
                      7,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      27,
                      'Short linho, 38, bege',
                      139.0,
                      4,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      28,
                      'Terninho, M , preto',
                      329.0,
                      5,
                      3
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      29,
                      'Cardigan, uva, tamanho único',
                      219.0,
                      4,
                      4
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      30,
                      'Tricot laranja, tamanho único',
                      89.0,
                      3,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      31,
                      'Camiseta, alça,G,branca',
                      '19,90',
                      1,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      32,
                      'Camisa social, listrada,GG',
                      129.0,
                      1,
                      1
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      33,
                      'Cropped, renda, P, branco',
                      '29,90',
                      2,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      34,
                      'Bolsa, preta, sintética',
                      89.0,
                      4,
                      2
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      35,
                      'Bolsa, marron, couro ',
                      '899,90',
                      5,
                      0
                  );

INSERT INTO ROUPA (
                      id_roupa,
                      nome,
                      valor,
                      qtd,
                      qtd_em_condicional
                  )
                  VALUES (
                      36,
                      'Short, cinza, M',
                      39.0,
                      1,
                      1
                  );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
