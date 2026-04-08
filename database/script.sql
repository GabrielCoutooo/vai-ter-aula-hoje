
CREATE DATABASE vai_ter_aula;
USE vai_ter_aula;


CREATE TABLE turma (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL
);

CREATE TABLE usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         tipo ENUM('ALUNO', 'PROFESSOR') NOT NULL
);

CREATE TABLE matricula (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           aluno_id INT NOT NULL,
                           turma_id INT NOT NULL,
                           FOREIGN KEY (aluno_id) REFERENCES usuario(id),
                           FOREIGN KEY (turma_id) REFERENCES turma(id)
);

CREATE TABLE aula (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      turma_id INT NOT NULL,
                      professor_id INT NOT NULL,
                      nome_disciplina VARCHAR(100) NOT NULL,
                      dia_semana ENUM('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA') NOT NULL,
                      horario_inicio TIME NOT NULL,
                      status ENUM('NORMAL', 'CANCELADA') DEFAULT 'NORMAL',
                      data_status DATE DEFAULT NULL,
                      FOREIGN KEY (turma_id) REFERENCES turma(id),
                      FOREIGN KEY (professor_id) REFERENCES usuario(id)
);

CREATE TABLE notificacao (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             turma_id INT NOT NULL,
                             professor_id INT NOT NULL,
                             mensagem TEXT NOT NULL,
                             data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (turma_id) REFERENCES turma(id),
                             FOREIGN KEY (professor_id) REFERENCES usuario(id)
);

-- 3. POPULANDO DADOS DE TESTE (DML)

-- Turmas/Disciplinas
INSERT INTO turma (id, nome) VALUES (1, '4 POA'), (2, '4 UBD'), (3, '4 MOD');

-- Professores (IDs 1 e 2)
INSERT INTO usuario (id, nome, email, senha, tipo) VALUES
                                                       (1, 'Professor Miguel', 'miguel@faeterj-rio.edu.br', '123', 'PROFESSOR'),
                                                       (2, 'Professor Vinícius', 'vinicius@faeterj-rio.edu.br', '123', 'PROFESSOR');

-- Alunos (Gabriel ID 3 e Lucas ID 4)
INSERT INTO usuario (id, nome, email, senha, tipo) VALUES
                                                       (3, 'Gabriel Couto', 'gabriel@gmail.com', '123', 'ALUNO'),
                                                       (4, 'Lucas Goyannes', 'lucas@gmail.com', '123', 'ALUNO');

-- MATRÍCULAS (O vínculo necessário para o JOIN funcionar)
-- Matriculando o Gabriel (3) nas 3 turmas para ele ver a grade completa
INSERT INTO matricula (aluno_id, turma_id) VALUES (3, 1), (3, 2), (3, 3);

-- GRADE DE AULAS
INSERT INTO aula (turma_id, professor_id, nome_disciplina, dia_semana, horario_inicio, status) VALUES
                                                                                                   (1, 1, '4 POA', 'SEXTA',   '08:50:00', 'NORMAL'),
                                                                                                   (2, 2, '4 UBD', 'QUINTA',  '08:50:00', 'NORMAL'),
                                                                                                   (3, 2, '4 MOD', 'SEGUNDA', '07:10:00', 'NORMAL');

-- 4. CONSULTA DE CONFERÊNCIA
SELECT 'Resumo de Matrículas' as info;
SELECT u.nome as Aluno, t.nome as Disciplina
FROM matricula m
         JOIN usuario u ON m.aluno_id = u.id
         JOIN turma t ON m.turma_id = t.id;