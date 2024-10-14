CREATE SEQUENCE carrinho_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Carrinho (
    id BIGINT DEFAULT nextval('carrinho_seq') PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_carrinho FOREIGN KEY (usuario_id) REFERENCES usuario(id)
    );

CREATE SEQUENCE item_carrinho_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Item_Carrinho (
    id BIGINT DEFAULT nextval('item_carrinho_seq') PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    carrinho_id BIGINT NOT NULL,
    quantidade INT NOT NULL DEFAULT 0,
    observacao VARCHAR(500),
    tamanho_copo VARCHAR(20),
    CONSTRAINT fk_produto_item_carrinho FOREIGN KEY (produto_id) REFERENCES produto(id),
    CONSTRAINT fk_carrinho_item_carrinho FOREIGN KEY (carrinho_id) REFERENCES carrinho(id)
    );

