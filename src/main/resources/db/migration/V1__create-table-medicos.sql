CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especializacao VARCHAR(100) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20)
);
