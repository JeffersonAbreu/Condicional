SELECT
  id_condicional,
  id_cliente,
  id_atendente,
  data_hora,
  valor,
  qtd,
  ativo
FROM
  CONDICIONAL
WHERE
  ativo = 1;