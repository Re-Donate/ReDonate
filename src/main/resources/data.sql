INSERT INTO usuario (email_usuario, senha_usuario) VALUES ("teste@teste.com", "123"), ("teste2@teste.com", "123"), ("teste3@teste.com", "123"), ("teste4@teste.com", "123"), ("teste5@teste.com", "123");

INSERT INTO doador (nome_doador, endereco_doador, cidade_doador, telefone_doador, celular_doador, cpf_doador, idade_doador, sexo_doador, id_usuario) VALUES ("Lucas Vinicius", "Av. João Palma Aleman", "Guarulhos", "", "11 954612022", "51323847812", "20", "Masculino", 1);

INSERT INTO instituicao (nome_instituicao, endereco_instituicao, cidade_instituicao, telefone_instituicao, celular_instituicao, cnpj_instituicao, cpf_instituicao, causa_instituicao, necessidades_instituicao, id_usuario) VALUES ("Turma do Peludo", "Rua Aureliano Coutinho", "Petrópolis", "", "11 923612886", "", "16833609615", "Cuidado e Adoção de animais de rua", "Ração;Remédios;Dinheiro", 2),
                                                                                                                                                                                                                         ("Sociedade São Vincente de Paulo", "Rua Augusta", "São Paulo", "11 23429084", "", "10277432910845", "", "Auxílio de pessoas carentes", "Dinheiro;Roupas;Alimento;Eletrodomésticos", 3),
                                                                                                                                                                                                                         ("Maria Bernadette Leme Monteiro Irma", "Rua Garção Tinoco", "São Paulo", "11 31038488", "", "112849387275490", "", "Inclusão educacional", "Dinheiro;Livros Paradidáticos;Material de Escola", 4),
                                                                                                                                                                                                                         ("A Benefiência Portuguesa de São Paulo", "Rua General Sócrates", "São Paulo", "11 350510000", "", "11454867398789", "", "Serviços médicos", "Dinheiro;Máscaras faciais;Alimento", 5);