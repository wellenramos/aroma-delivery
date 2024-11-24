CREATE SEQUENCE item_adicional_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Item_Adicional (
    id BIGINT DEFAULT nextval('item_adicional_seq') PRIMARY KEY,
    item_carrinho_id BIGINT NOT NULL,
    item_adicional_id BIGINT NOT NULL,
    CONSTRAINT fk_item_carrinho FOREIGN KEY (item_carrinho_id) REFERENCES item_carrinho (id),
    CONSTRAINT fk_item_adicional FOREIGN KEY (item_adicional_id) REFERENCES produto (id)
);
