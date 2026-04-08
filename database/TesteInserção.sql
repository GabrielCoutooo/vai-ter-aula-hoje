USE vai_ter_aula;

-- 1. Criando a Turma base (O ID dela será 1)
INSERT INTO turma (nome) VALUES ('POA - 4º Período - Faeterj-Rio');

-- 2. Criando os Professores (Professor não fica preso a uma única turma, então turma_id é NULL)
INSERT INTO usuario (nome, email, senha, tipo, turma_id) VALUES 
('Professor Miguel', 'miguel@faeterj-rio.edu.br', '123', 'PROFESSOR', NULL),
('Professor Vinícius', 'vinicius@faeterj-rio.edu.br', '123', 'PROFESSOR', NULL);

-- 3. Criando os Alunos (Todo o grupo vinculado à Turma de ID 1)
INSERT INTO usuario (nome, email, senha, tipo, turma_id) VALUES 
('Gabriel Couto', 'gabriel@gmail.com', '123', 'ALUNO', 1),
('Lucas Goyannes', 'lucas@gmail.com', '123', 'ALUNO', 1),
('Luís Davi', 'luis@gmail.com', '123', 'ALUNO', 1),
('Sérgio Carlesso', 'sergio@gmail.com', '123', 'ALUNO', 1);

-- 4. Vamos ver como ficou? (Esse comando mostra os usuários criados)
SELECT * FROM usuario;