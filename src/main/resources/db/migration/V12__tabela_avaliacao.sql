CREATE SEQUENCE avaliacao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Avaliacao (
    id BIGINT DEFAULT nextval('avaliacao_seq') PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    nota INT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario VARCHAR(500),
    CONSTRAINT fk_pedido_avaliacao FOREIGN KEY (pedido_id) REFERENCES Pedido(id)
);
