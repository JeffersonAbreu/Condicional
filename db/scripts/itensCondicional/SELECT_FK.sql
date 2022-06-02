SELECT
    id_condicional,
    id_roupa,
    valor_uni,
    qtd,
    valor_total
FROM
    ITENS_CONDICIONAL
WHERE
    id_condicional = ?;