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
    public List<Aula> buscarPorTurma(int turmaId){
        List<Aula> grade = new ArrayList<>();
        String sql = "SELECT * FROM aula WHERE turma_id = ? ORDER BY FIELD(dia_semana,'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA'), horario_inicio";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, turmaId);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Aula aula = new Aula();
                    aula.setId(rs.getInt("id"));
                    aula.setTurmaId(rs.getInt("turma_id"));
                    aula.setProfessorId(rs.getInt("professor_id"));
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
}
