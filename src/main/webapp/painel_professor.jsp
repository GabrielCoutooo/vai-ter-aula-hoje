<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, main.java.com.vaiteraula.model.Aula" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Painel do Professor - Vai Ter Aula Hoje?</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="dashboard-body">

<header class="topbar">
    <div class="topbar-logo">
        <img src="img/logo.jpeg" alt="Logo Coruja" class="logo-mini">
        <h2>Painel Docente</h2>
    </div>
    <div class="topbar-user">
        <span class="user-name">Prof. ${usuario.nome}</span>
        <a href="logout" class="logout-btn"><span class="material-symbols-outlined">logout</span></a>
    </div>
</header>

<main class="dashboard-container">
    <div class="dashboard-header">
        <h1>Minhas Disciplinas</h1>
        <p>Gerencie o status das suas aulas em tempo real.</p>
    </div>

    <section class="classes-section">
        <div class="class-list">
            <%
                List<Aula> aulas = (List<Aula>) request.getAttribute("aulas");
                if (aulas != null) {
                    for (Aula a : aulas) {
                        boolean isCancelada = "CANCELADA".equals(a.getStatus());
            %>
            <div class="class-item <%= isCancelada ? "status-cancelled" : "status-confirmed" %>">
                <div class="class-time">
                    <span class="material-symbols-outlined">schedule</span>
                    <div><strong><%= a.getDiaSemana() %></strong><br><%= a.getHorarioInicio() %></div>
                </div>
                <div class="class-details">
                    <h4><%= a.getNomeDisciplina() %></h4>
                    <p>Turma ID: <%= a.getTurmaId() %></p>
                </div>
                <div class="class-status">
                    <form action="painel-professor" method="POST" style="display:inline;">
                        <input type="hidden" name="acao" value="toggleStatus">
                        <input type="hidden" name="id" value="<%= a.getId() %>">
                        <input type="hidden" name="statusAtual" value="<%= a.getStatus() %>">

                        <button type="submit" class="badge <%= isCancelada ? "badge-red" : "badge-green" %>" style="border:none; cursor:pointer;">
                            <%= isCancelada ? "REATIVAR AULA" : "CANCELAR AULA" %>
                        </button>
                    </form>
                </div>
            </div>
            <% } } %>
        </div>
    </section>
</main>

</body>
</html>