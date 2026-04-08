package main.java.com.vaiteraula.model;

import java.time.LocalDateTime;

public class Notificacao {
    private int id;
    private int turmaId;
    private int professorId;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public Notificacao(){

    }
    public Notificacao(int id,int turmaId,int professorId,String mensagem,LocalDateTime dataCriacao){
        this.id = id;
        this.turmaId = turmaId;
        this.professorId = professorId;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
