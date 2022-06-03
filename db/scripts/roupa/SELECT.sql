SELECT
  id_roupa,
  nome,
  valor,
  qtd,
  qtd_em_condicional
FROM
  ROUPA
WHERE
  id_roupa = ?;