ALTER TABLE medicos 
ADD COLUMN telefone VARCHAR(20) NOT NULL DEFAULT 'Sem telefone';

ALTER TABLE medicos 
ALTER COLUMN telefone DROP DEFAULT;