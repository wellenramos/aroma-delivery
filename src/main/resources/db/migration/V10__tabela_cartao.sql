CREATE SEQUENCE cartao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Cartao (
    id BIGINT DEFAULT nextval('cartao_seq') PRIMARY KEY,
    numero VARCHAR(16) NOT NULL,
    titular VARCHAR(100) NOT NULL,
    validade_mes SMALLINT NOT NULL,
    validade_ano SMALLINT NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    bandeira VARCHAR(50),
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_cartao FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    CONSTRAINT numero_unique UNIQUE (numero)
);
