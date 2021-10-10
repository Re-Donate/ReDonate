INSERT INTO usuario (email_usuario, senha_usuario, nome_usuario, endereco_usuario, cidade_usuario, estado_usuario, telefone_usuario, celular_usuario, cpf_usuario) VALUES ("teste@teste.com", "123", "Lucas Vinicius", "Av. João Palma Aleman", "Guarulhos", "São Paulo", "", "(11) 95461-2022", "513.238.478-12"),
                                                                                                                                                                          ("teste2@teste.com", "123", "Turma do Peludo", "Rua Aureliano Coutinho", "Petrópolis", "Rio de Janeiro", "", "(11) 92361-2886", "168.336.096-15"),
                                                                                                                                                                          ("teste3@teste.com", "123", "Sociedade São Vincente de Paulo", "Rua Augusta", "São Paulo", "São Paulo", "(11) 2342-9084", "",  ""),
                                                                                                                                                                          ("teste4@teste.com", "123", "Maria Bernadette Leme Monteiro Irma", "Rua Garção Tinoco", "São Paulo", "São Paulo", "(11) 3103-8488", "", ""),
                                                                                                                                                                          ("teste5@teste.com", "123", "A Benefiência Portuguesa de São Paulo", "Rua General Sócrates", "São Paulo", "São Paulo", "(11) 5051-0020", "", "");

INSERT INTO doador (nascimento_doador, sexo_doador, id_usuario) VALUES (DATE '2001-06-26', "Masculino", 1);

INSERT INTO instituicao (cnpj_instituicao, causa_instituicao, necessidades_instituicao, id_usuario) VALUES ("", "Cuidado e Adoção de animais de rua", "Produtos para Pets,Remédios,Outros fins", 2),
                                                                                                           ("10.277.432/9108-45", "Auxílio de pessoas carentes", "Vestimentas,Alimentos,Outros fins", 3),
                                                                                                           ("11.284.938/7275-90", "Inclusão educacional", "Outros fins,Educação", 4),
                                                                                                           ("11.454.867/3987-89", "Serviços médicos", "Outros fins,Alimentos", 5);

INSERT INTO doacao (valor_doacao, causa_doacao, id_doador, id_instituicao) VALUES (25, "Outros fins", 1, 4),
                                                                                  (30, "Alimentos", 1, 4);