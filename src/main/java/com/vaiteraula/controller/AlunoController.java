package main.java.com.vaiteraula.controller;

import main.java.com.vaiteraula.model.Aula;
import main.java.com.vaiteraula.model.Notificacao;
import main.java.com.vaiteraula.model.Usuario;
import main.java.com.vaiteraula.repository.AulaRepository;
import main.java.com.vaiteraula.repository.NotificacaoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/painel-aluno")
public class AlunoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null) {
            response.sendRedirect("index.html");
            return;
        }

        int alunoId = usuarioLogado.getId();


        AulaRepository aulaRepo = new AulaRepository();
        List<Aula> listaAulas = aulaRepo.buscarAulasDoAluno(alunoId);


        NotificacaoRepository notifRepo = new NotificacaoRepository();
        List<Notificacao> listaNotif = notifRepo.buscarPorAluno(alunoId);


        request.setAttribute("aulas", listaAulas);
        request.setAttribute("notificacoes", listaNotif);

        request.getRequestDispatcher("painel_aluno.jsp").forward(request, response);
    }
}
