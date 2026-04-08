package main.java.com.vaiteraula.repository;

import main.java.com.vaiteraula.model.Aula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AulaRepository {
    /**
     * Ação do ALUNO: Ver a grade horária da semana para a turma dele.
     */
    public List<Aula> buscarAulasDoAluno(int alunoId){
        List<Aula> grade = new ArrayList<>();
        String sql = "SELECT a.* FROM aula a " +
                "JOIN matricula m ON a.turma_id = m.turma_id " +
                "WHERE m.aluno_id = ? " +
                "ORDER BY FIELD(a.dia_semana, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA'), a.horario_inicio";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Aula aula = new Aula();
                    aula.setId(rs.getInt("id"));
                    aula.setNomeDisciplina(rs.getString("nome_disciplina"));
                    aula.setDiaSemana(rs.getString("dia_semana"));
                    aula.setHorarioInicio(rs.getString("horario_inicio"));
                    aula.setStatus(rs.getString("status"));
                    grade.add(aula);
                }

            }
        }catch (SQLException e ){
            System.out.println("Erro ao buscar grade horária: "+e.getMessage());
        }
        return grade;
    }
    /**
     * Ação do PROFESSOR: Cancelar uma aula específica.
     * Recebe o ID da aula e o novo status (CANCELADA).
     */
    public boolean atualizarStatus(int aulaId,String novoStatus){
        String sql = "UPDATE aula SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){
                 stmt.setString(1, novoStatus);
                 stmt.setInt(2,aulaId);
                 return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            System.out.println("Erro ao atualizar status da aula: "+e.getMessage());
            return false;
        }
    }
    public List<Aula> buscarAulasDoProfessor(int professorId) {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT * FROM aula WHERE professor_id = ? ORDER BY FIELD(dia_semana, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA'), horario_inicio";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aula a = new Aula();
                    a.setId(rs.getInt("id"));
                    a.setTurmaId(rs.getInt("turma_id"));
                    a.setNomeDisciplina(rs.getString("nome_disciplina"));
                    a.setDiaSemana(rs.getString("dia_semana"));
                    a.setHorarioInicio(rs.getString("horario_inicio"));
                    a.setStatus(rs.getString("status"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public Aula buscarPorId(int id) {
        String sql = "SELECT * FROM aula WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Aula a = new Aula();
                    a.setId(rs.getInt("id"));
                    a.setTurmaId(rs.getInt("turma_id"));
                    a.setProfessorId(rs.getInt("professor_id"));
                    a.setNomeDisciplina(rs.getString("nome_disciplina"));
                    a.setDiaSemana(rs.getString("dia_semana"));
                    a.setStatus(rs.getString("status"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
