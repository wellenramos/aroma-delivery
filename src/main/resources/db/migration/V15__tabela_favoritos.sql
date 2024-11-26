CREATE SEQUENCE favoritos_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Favoritos (
    id BIGINT DEFAULT nextval('favoritos_seq') PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_favorito FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    CONSTRAINT fk_produt_favorito FOREIGN KEY (produto_id) REFERENCES produto (id)
);
