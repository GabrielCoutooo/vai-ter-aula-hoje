package main.java.com.vaiteraula.repository;

import main.java.com.vaiteraula.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public Usuario fazerLogin(String email,String senha){
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        Usuario usuarioLogado = null;
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,email);
            stmt.setString(2, senha);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    usuarioLogado = new Usuario();
                    usuarioLogado.setId(rs.getInt("id"));
                    usuarioLogado.setNome(rs.getString("nome"));
                    usuarioLogado.setEmail(rs.getString("email"));
                    usuarioLogado.setSenha(rs.getString("senha"));
                    usuarioLogado.setTipo(rs.getString("tipo"));
                    usuarioLogado.setTurmaId(rs.getInt("turma_id"));
                }
            }
        }catch (SQLException e ){
            System.out.println("Erro ao tentar fazer login: "+e.getMessage());
        }
        return usuarioLogado;
    }



    // Método temporário só para testar a lógica
    public static void main(String[] args) {
        UsuarioRepository repo = new UsuarioRepository();

        System.out.println("Testando o login do Professor...");

        Usuario usuarioLogado = repo.fazerLogin("miguel@faeterj.edu.br", "123");

        if (usuarioLogado != null) {
            System.out.println("SUCESSO! Usuário encontrado no banco!");
            System.out.println("Nome: " + usuarioLogado.getNome());
            System.out.println("Tipo de Conta: " + usuarioLogado.getTipo());
        } else {
            System.out.println("Falha! Usuário ou senha incorretos.");
        }
    }
}
