CREATE SEQUENCE endereco_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Endereco (
    id BIGINT DEFAULT nextval('endereco_seq') PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    cep VARCHAR(20),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    principal BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);


CREATE SEQUENCE endereco_base_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Endereco_Base (
    id BIGINT DEFAULT nextval('endereco_base_seq') PRIMARY KEY,
    cep VARCHAR(20) NOT NULL,
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50)
);
