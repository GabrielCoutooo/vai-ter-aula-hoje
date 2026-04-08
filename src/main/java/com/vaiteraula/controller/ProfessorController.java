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

@WebServlet("/painel-professor")
public class ProfessorController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");

        if (user == null || !user.getTipo().equals("PROFESSOR")) {
            response.sendRedirect("index.html");
            return;
        }

        AulaRepository repo = new AulaRepository();
        request.setAttribute("aulas", repo.buscarAulasDoProfessor(user.getId()));
        request.getRequestDispatcher("painel_professor.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        AulaRepository repo = new AulaRepository();
        NotificacaoRepository notifRepo = new NotificacaoRepository();

        if ("toggleStatus".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String atual = request.getParameter("statusAtual");
            String novo = atual.equals("NORMAL") ? "CANCELADA" : "NORMAL";

            boolean sucesso = repo.atualizarStatus(id, novo);

            //Se a aula foi cancelada com sucesso, gera a notificação
            if (sucesso && novo.equals("CANCELADA")) {
                Aula aula = repo.buscarPorId(id);

                if (aula != null) {
                    Notificacao n = new Notificacao();
                    n.setTurmaId(aula.getTurmaId());
                    n.setProfessorId(aula.getProfessorId());
                    n.setMensagem("A aula de " + aula.getNomeDisciplina() + " do dia " + aula.getDiaSemana() + " foi CANCELADA.");

                    notifRepo.salvar(n);
                }
            }
        }

        response.sendRedirect("painel-professor");
    }
}
