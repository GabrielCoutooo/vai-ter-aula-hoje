package main.java.com.vaiteraula.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Suas credenciais originais (mantidas intactas)
    private static final String URL = "jdbc:mysql://localhost:3306/vai_ter_aula?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // O método conectar() turbinado para rodar no Tomcat
    public static Connection conectar() {
        try {
            // Avisa ao Tomcat para carregar o motor do MySQL na memória
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco estabelecida com sucesso!");
            return conn;

        } catch (ClassNotFoundException e) {
            // Trata o erro se o IntelliJ esquecer de colocar o conector no pacote
            System.err.println("Erro Crítico: O Driver do MySQL não foi encontrado!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            // Trata o erro se a senha estiver errada ou o XAMPP desligado
            System.err.println("Falha ao se conectar ao banco!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        conectar();
    }
}
