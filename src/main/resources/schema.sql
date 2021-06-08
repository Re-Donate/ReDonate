DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id INT NOT NULL AUTO_INCREMENT,
    email_usuario VARCHAR(30) NOT NULL UNIQUE,
    senha_usuario VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS doador;
CREATE TABLE doador (
    id INT NOT NULL AUTO_INCREMENT,
    nome_doador VARCHAR(30) NOT NULL,
    endereco_doador VARCHAR(30) NOT NULL,
    cidade_doador VARCHAR(30) NOT NULL,
    telefone_doador VARCHAR(30) NOT NULL,
    celular_doador VARCHAR(30) NOT NULL,
    cpf_doador VARCHAR(30) NOT NULL,
    idade_doador VARCHAR(30) NOT NULL,
    sexo_doador VARCHAR(30) NOT NULL,
    id_usuario INT NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS instituicao;
CREATE TABLE instituicao (
    id INT NOT NULL AUTO_INCREMENT,
    nome_instituicao VARCHAR(100) NOT NULL,
    endereco_instituicao VARCHAR(100) NOT NULL,
    cidade_instituicao VARCHAR(30) NOT NULL,
    telefone_instituicao VARCHAR(30) NOT NULL,
    celular_instituicao VARCHAR(30) NOT NULL,
    cnpj_instituicao VARCHAR(30) NOT NULL,
    cpf_instituicao VARCHAR(30) NOT NULL,
    causa_instituicao VARCHAR(250) NOT NULL,
    necessidades_instituicao VARCHAR(100) NOT NULL,
    id_usuario INT NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS doacao;
CREATE TABLE doacao (
    id INT NOT NULL AUTO_INCREMENT,
    valor_doacao FLOAT NOT NULL,
    causa_doacao VARCHAR(30) NOT NULL,
    id_doador INT NOT NULL,
    id_instituicao INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
