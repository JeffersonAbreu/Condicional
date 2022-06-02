SELECT
  id_roupa,
  nome,
  valor,
  cod_barras,
  qtd,
  qtd_em_condicional
FROM
  ROUPA
WHERE
  id_roupa = ?;