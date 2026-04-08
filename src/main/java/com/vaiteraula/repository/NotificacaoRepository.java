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
    public List<Notificacao> buscarPorTurma(int turmaId){
        List<Notificacao> listaAvisos = new ArrayList<>();
        String sql = "SELECT * FROM notificacao WHERE turma_id = ? ORDER BY data_criacao DESC";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1,turmaId);
                try(ResultSet rs = stmt.executeQuery()) {
                    //Enquanto estiver linhas no resultado da consulta ao banco
                    while(rs.next()){
                        Notificacao notif = new Notificacao();
                        notif.setId(rs.getInt("id"));
                        notif.setTurmaId(rs.getInt("turma_id"));
                        notif.setProfessorId(rs.getInt("professor_id"));
                        notif.setMensagem(rs.getString("mensagem"));
                        notif.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                        listaAvisos.add(notif);
                    }
                }
        } catch (SQLException e){
            System.out.println("Erro ao buscar notificações: "+e.getMessage());
        }
        return listaAvisos;
    }
}
