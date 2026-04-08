-- Criação do banco de dados
CREATE DATABASE vai_ter_aula;
USE vai_ter_aula;

-- 1. Tabela de Turmas
-- Simples e direta. Ex: "ADS - 3º Período"
CREATE TABLE turma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- 2. Tabela de Usuários (Serve tanto para Alunos quanto Professores)
-- Usamos um ENUM para diferenciar quem é quem.
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo ENUM('ALUNO', 'PROFESSOR') NOT NULL,
    turma_id INT, -- Se for professor, esse campo fica vazio (NULL)
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

-- 3. Tabela de Aulas (A grade horária)
-- Aqui cadastramos as matérias, horários e, o mais importante, o STATUS da aula.
CREATE TABLE aula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    professor_id INT NOT NULL,
    nome_disciplina VARCHAR(100) NOT NULL,
    dia_semana ENUM('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA') NOT NULL,
    horario_inicio TIME NOT NULL,
    status ENUM('NORMAL', 'CANCELADA') DEFAULT 'NORMAL',
    FOREIGN KEY (turma_id) REFERENCES turma(id),
    FOREIGN KEY (professor_id) REFERENCES usuario(id)
);

-- 4. Tabela de Notificações
-- Onde ficam os recados e motivos de cancelamento.
CREATE TABLE notificacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    professor_id INT NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (turma_id) REFERENCES turma(id),
    FOREIGN KEY (professor_id) REFERENCES usuario(id)
);