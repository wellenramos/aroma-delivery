CREATE TABLE perfil
(
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

INSERT INTO perfil (nome) VALUES ('admin');
INSERT INTO perfil (nome) VALUES ('cliente');''
