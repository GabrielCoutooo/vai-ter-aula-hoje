package main.java.com.vaiteraula.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/vai_ter_aula?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar(){
        try {
            Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexão com o banco estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Falha ao se conectar ao banco!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        conectar();
    }
}
