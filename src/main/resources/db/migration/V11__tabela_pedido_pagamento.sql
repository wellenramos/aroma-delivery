CREATE SEQUENCE pedido_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Pedido (
    id BIGINT DEFAULT nextval('pedido_seq') PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    endereco_id BIGINT NOT NULL,
    data_solicitacao DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    favorito BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_usuario_pedido FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    CONSTRAINT fk_endereco_pedido FOREIGN KEY (endereco_id) REFERENCES Endereco(id)
);

CREATE SEQUENCE pagamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Pagamento (
    id BIGINT DEFAULT nextval('pagamento_seq') PRIMARY KEY,
    pedido_id BIGINT UNIQUE NOT NULL,
    cartao_id BIGINT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_pagamento DATE NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL,
    CONSTRAINT fk_pedido_pagamento FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    CONSTRAINT fk_cartao_pagamento FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);



ALTER TABLE Item_Carrinho ADD COLUMN pedido_id BIGINT;

ALTER TABLE Item_Carrinho
    ADD CONSTRAINT fk_pedido_item_carrinho
        FOREIGN KEY (pedido_id) REFERENCES pedido(id);