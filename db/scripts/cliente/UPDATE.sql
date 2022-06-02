UPDATE CLIENTE
   SET nome = ?,
       data_nascimento = ?,
       cpf = ?,
       email = ?,
       telefone = ?,
       celular = ?,
       logradouro = ?,
       bairro = ?,
       cidade = ?,
       uf = ?,
       cep = ?,
       limite = ?
 WHERE id_cliente = ?;