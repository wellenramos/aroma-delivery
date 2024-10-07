CREATE SEQUENCE usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Usuario (
    id BIGINT DEFAULT nextval('usuario_seq') PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil_id BIGINT,
    CONSTRAINT fk_perfil FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);