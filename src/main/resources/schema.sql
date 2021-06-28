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

DROP TABLE IF EXISTS doador;
CREATE TABLE doador (
                        id INT NOT NULL AUTO_INCREMENT,
                        nascimento_doador DATE NOT NULL,
                        sexo_doador VARCHAR(30) NOT NULL,
                        id_usuario INT NOT NULL UNIQUE,
                        PRIMARY KEY (id)
);

DROP TABLE IF EXISTS instituicao;
CREATE TABLE instituicao (
                             id INT NOT NULL AUTO_INCREMENT,
                             cnpj_instituicao VARCHAR(30),
                             causa_instituicao VARCHAR(250) NOT NULL,
                             necessidades_instituicao VARCHAR(100) NOT NULL,
                             id_usuario INT NOT NULL UNIQUE,
                             PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id INT NOT NULL AUTO_INCREMENT,
    email_usuario VARCHAR(30) NOT NULL UNIQUE,
    senha_usuario VARCHAR(30) NOT NULL,
    nome_usuario VARCHAR(300) NOT NULL,
    endereco_usuario VARCHAR(30) NOT NULL,
    cidade_usuario VARCHAR(30) NOT NULL,
    estado_usuario VARCHAR(30) NOT NULL,
    telefone_usuario VARCHAR(30),
    celular_usuario VARCHAR(30),
    cpf_usuario VARCHAR(30),
    PRIMARY KEY (id)
);

ALTER TABLE doador
    ADD CONSTRAINT `fk_id_usuario_doador` FOREIGN KEY (id_usuario) REFERENCES usuario (id);

ALTER TABLE instituicao
    ADD CONSTRAINT `fk_id_usuario_instituicao` FOREIGN KEY (id_usuario) REFERENCES usuario (id);

ALTER TABLE doacao
    ADD CONSTRAINT `fk_id_doador` FOREIGN KEY (id_doador) REFERENCES doador (id),
    ADD CONSTRAINT `fk_id_instituicao` FOREIGN KEY (id_instituicao) REFERENCES instituicao (id);
