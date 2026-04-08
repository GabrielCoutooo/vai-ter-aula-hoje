package main.java.com.vaiteraula.repository;

import main.java.com.vaiteraula.model.Notificacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoRepository {
    /**
     * Ação do PROFESSOR: Criar um novo aviso para a turma.
     */
    public boolean criar(Notificacao notificacao){
        String sql = "INSERT INTO notificacao (turma_id,professor_id,mensagem) VALUES (?, ?, ?)";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,notificacao.getTurmaId());
            stmt.setInt(2,notificacao.getProfessorId());
            stmt.setString(3,notificacao.getMensagem());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se conseguir salvar
        }catch (SQLException e ){
            System.out.println("Erro ao criar notificação: "+e.getMessage());
            return false;
        }
    }
    /**
     * Ação do ALUNO: Buscar todos os avisos da sua própria turma.
     */
    public List<Notificacao> buscarPorAluno(int alunoId) {
        List<Notificacao> listaAvisos = new ArrayList<>();
        String sql = "SELECT n.* FROM notificacao n " +
                "JOIN matricula m ON n.turma_id = m.turma_id " +
                "WHERE m.aluno_id = ? " +
                "ORDER BY n.data_criacao DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notificacao notif = new Notificacao();
                    notif.setId(rs.getInt("id"));
                    notif.setMensagem(rs.getString("mensagem"));
                    notif.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                    listaAvisos.add(notif);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar notificações do aluno: " + e.getMessage());
        }
        return listaAvisos;
    }
    public boolean salvar(Notificacao n) {
        String sql = "INSERT INTO notificacao (turma_id, professor_id, mensagem) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, n.getTurmaId());
            stmt.setInt(2, n.getProfessorId());
            stmt.setString(3, n.getMensagem());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
