CREATE SEQUENCE produto_adicional_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Produto_Adicional (
    id BIGINT DEFAULT nextval('produto_adicional_seq') PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    adicional_id BIGINT NOT NULL,
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES Produto(id),
    CONSTRAINT fk_adicional FOREIGN KEY (adicional_id) REFERENCES Produto(id),
    CONSTRAINT produto_adicional_unique UNIQUE (produto_id, adicional_id)
);
