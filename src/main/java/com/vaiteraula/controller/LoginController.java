package main.java.com.vaiteraula.controller;


import main.java.com.vaiteraula.model.Usuario;
import main.java.com.vaiteraula.repository.UsuarioRepository;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// Esta anotação "liga" o formulário HTML (action="login") com esta classe Java
@WebServlet("/login")
public class LoginController extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioRepository repo = new UsuarioRepository();
        Usuario usuarioLogado = repo.fazerLogin(email,senha);
        if(usuarioLogado != null){
            //Criando sessão
            HttpSession session = request.getSession();
            session.setAttribute("usuario",usuarioLogado);
            //Verificando o tipo do usuario para saber qual tela mandar
            if(usuarioLogado.getTipo().equals("ALUNO")){
                response.sendRedirect("painel-aluno");
            }else if(usuarioLogado.getTipo().equals("PROFESSOR")){
                response.sendRedirect("painel-professor");
            }
        }else{
            // E-mail ou senha incorretos. Manda o usuario de volta pro login avisando do erro
            response.sendRedirect("index.html?erro=true");
        }
    }
}
