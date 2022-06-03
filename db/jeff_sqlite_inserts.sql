INSERT INTO
    ATENDENTE (
        nome,
        login,
        senha
    )
VALUES
    (
        'Ana',
        'ana',
        '123'
    ),
    (
        'Beatriz',
        'bia',
        '123'
    );

INSERT INTO
    CLIENTE (
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
VALUES
    (
        'Luciana V. Moreira',
        '32516305200000',
        '11111111111',
        'lu@moreira.com',
        '',
        '',
        'Rua Ceu Claro',
        'Alto',
        'Vitória',
        'ES',
        '29000000',
        1000.0
    ),
    (
        'Juliana Garcia',
        '32516305200000',
        '11111111111',
        'lu@moreira.com',
        '',
        '',
        'Rua Ceu Claro',
        'Alto',
        'Vitória',
        'ES',
        '29000000',
        100.0
    ),
    (
        'Helen',
        '32516305200000',
        '22222222233',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        200.0
    );

INSERT INTO
    ROUPA (
        nome,
        valor,
        qtd,
        qtd_em_condicional
    )
VALUES
    (
        'Jaqueta de Couro - Preta',
        '300,00',
        3,
        1
    ),
    (
        'Jaqueta Jeans - Azul',
        '199,99',
        5,
        0
    ),
    (
        'Vestido Longo - Verde',
        '187,50',
        10,
        0
    ),
    (
        'Vestito Curto - Branco',
        '99,00',
        7,
        0
    ),
    (
        'Vestido Curto - Preto',
        '129,90',
        3,
        0
    ),
    (
        'Calca Jeans - GG - Azul',
        '99,99',
        4,
        0
    ),
    (
        'Calca Jeans - G - Azul',
        '99,99',
        4,
        0
    ),
    (
        'Calca Jeans - M - Azul',
        '99,99',
        4,
        0
    ),
    (
        'Calca Jeans - P - Azul',
        '99,99',
        4,
        1
    ),
    (
        'Blusinha de Alca Basica - Branca',
        '59,99',
        3,
        1
    );

INSERT INTO
    CONDICIONAL (
        id_cliente,
        id_atendente,
        data_hora,
        valor,
        qtd,
        ativo
    )
VALUES
    (
        2,
        1,
        '947716055',
        '300,00',
        1,
        0
    ),
    (
        3,
        2,
        '947716055',
        '159,98',
        2,
        0
    );

INSERT INTO
    ITENS_CONDICIONAL (
        id_condicional,
        id_roupa,
        valor_uni,
        qtd,
        valor_total
    )
VALUES
    (
        1,
        1,
        '300,00',
        1,
        '300,00'
    ),
    (
        2,
        10,
        '59,99',
        1,
        '59,99'
    ),
    (
        2,
        9,
        '99,99',
        1,
        '99,99'
    );