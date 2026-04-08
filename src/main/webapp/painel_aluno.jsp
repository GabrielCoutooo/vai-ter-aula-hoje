<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.com.vaiteraula.model.Aula" %>
<%@ page import="main.java.com.vaiteraula.model.Notificacao" %>

<%
    //  DECLARAÇÕES ÚNICAS:  listas enviadas pelo AlunoController
    List<Aula> aulas = (List<Aula>) request.getAttribute("aulas");
    List<Notificacao> notificacoes = (List<Notificacao>) request.getAttribute("notificacoes");

    //  Lógica do Sininho
    int totalNotificacoes = (notificacoes != null) ? notificacoes.size() : 0;

    // 3. Lógica dos Cards (Contagem baseada no banco e no dia atual)
    int contadorHoje = 0;
    int contadorCanceladas = 0;

    // Descobrir o dia da semana atual para bater com seu ENUM no SQL
    java.time.DayOfWeek diaJava = java.time.LocalDate.now().getDayOfWeek();
    String hojeEnum = "";
    switch(diaJava) {
        case MONDAY:    hojeEnum = "SEGUNDA"; break;
        case TUESDAY:   hojeEnum = "TERCA";   break;
        case WEDNESDAY: hojeEnum = "QUARTA";  break;
        case THURSDAY:  hojeEnum = "QUINTA";  break;
        case FRIDAY:    hojeEnum = "SEXTA";   break;
        default:        hojeEnum = "FIM_DE_SEMANA";
    }

    if (aulas != null) {
        for (Aula a : aulas) {
            // Compara com o dia da semana da aula
            if (hojeEnum.equals(a.getDiaSemana())) {
                contadorHoje++;
            }
            // Verifica status conforme definido no seu script.sql
            if ("CANCELADA".equalsIgnoreCase(a.getStatus())) {
                contadorCanceladas++;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel do Aluno - Vai Ter Aula Hoje?</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="dashboard-body">

<header class="topbar">
    <div class="topbar-logo">
        <img src="img/logo.jpeg" alt="Logo Coruja" class="logo-mini">
        <h2>Vai Ter Aula Hoje?</h2>
    </div>
    <div class="topbar-user">
        <button class="notif-btn" id="btnNotif" title="Ver Notificações">
            <span class="material-symbols-outlined">notifications</span>
            <% if (totalNotificacoes > 0) { %>
            <span class="notif-badge"><%= totalNotificacoes %></span>
            <% } %>
        </button>

        <span class="material-symbols-outlined user-icon">school</span>
        <span class="user-name">Olá, ${usuario.nome}</span>
        <a href="logout" class="logout-btn" title="Sair" style="color: white; margin-left: 15px; text-decoration: none;">
            <span class="material-symbols-outlined">logout</span>
        </a>
    </div>
</header>

<main class="dashboard-container">
    <div class="dashboard-header">
        <h1>Minha Agenda</h1>
        <p>Confira o status das suas aulas e avisos dos professores.</p>
    </div>

    <section class="summary-cards" style="grid-template-columns: repeat(2, 1fr);">
        <div class="card">
            <div class="card-icon blue"><span class="material-symbols-outlined">calendar_today</span></div>
            <div class="card-info">
                <h3><%= contadorHoje %></h3>
                <p>Aulas Hoje</p>
            </div>
        </div>
        <div class="card">
            <div class="card-icon red"><span class="material-symbols-outlined">cancel</span></div>
            <div class="card-info">
                <h3><%= contadorCanceladas %></h3>
                <p>Aulas Canceladas</p>
            </div>
        </div>
    </section>

    <section class="classes-section">
        <h2>Quadro de Horários</h2>
        <div class="class-list">
            <%
                if (aulas != null && !aulas.isEmpty()) {
                    for (Aula aula : aulas) {
                        // Lógica de visual baseada no status
                        boolean vaiTerAula = "NORMAL".equalsIgnoreCase(aula.getStatus());
                        String statusClass = vaiTerAula ? "status-confirmed" : "status-cancelled";
                        String badgeClass = vaiTerAula ? "badge-green" : "badge-red";
                        String statusTexto = vaiTerAula ? "Vai Ter Aula" : "Cancelada";
            %>
            <div class="class-item <%= statusClass %>">
                <div class="class-time">
                    <span class="material-symbols-outlined">schedule</span>
                    <strong><%= aula.getDiaSemana() %></strong> <br>
                    <%= aula.getHorarioInicio() %>
                </div>
                <div class="class-details">
                    <h4><%= aula.getNomeDisciplina() %></h4>
                    <p>ID do Professor: <%= aula.getProfessorId() %></p>
                </div>
                <div class="class-status">
                    <span class="badge <%= badgeClass %>"><%= statusTexto %></span>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div style="background: white; padding: 20px; border-radius: 12px; text-align: center; color: #666;">
                <p><span class="material-symbols-outlined" style="vertical-align: middle;">info</span>
                    Você ainda não está matriculado em nenhuma disciplina ou não há aulas cadastradas.</p>
            </div>
            <% } %>
        </div>
    </section>
</main>

<div id="modalNotif" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2><span class="material-symbols-outlined">notifications</span> Avisos dos Professores</h2>
            <button class="close-modal" id="closeNotif">&times;</button>
        </div>
        <div class="modal-body">
            <%
                if (notificacoes != null && !notificacoes.isEmpty()) {
                    for (Notificacao n : notificacoes) {
            %>
            <div class="notif-item">
                <p><%= n.getMensagem() %></p>
                <small>Postado em: <%= n.getDataCriacao().toString().replace("T", " ") %></small>
            </div>
            <% } } else { %>
            <div style="padding: 30px; text-align: center; color: #888;">
                <span class="material-symbols-outlined" style="font-size: 80px;">notifications_off</span>
                <p>Nenhum aviso novo por aqui.</p>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script src="js/script.js"></script>
</body>
</html>