CREATE SEQUENCE item_adicional_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Tabela de associação entre produtos e seus itens adicionais
CREATE TABLE IF NOT EXISTS Item_Adicional (
    id BIGINT DEFAULT nextval('item_adicional_seq') PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    adicional_id BIGINT NOT NULL,
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES Produto(id),
    CONSTRAINT fk_adicional FOREIGN KEY (adicional_id) REFERENCES Produto(id),
    CONSTRAINT produto_adicional_unique UNIQUE (produto_id, adicional_id) -- Impede duplicidade de associação
);
