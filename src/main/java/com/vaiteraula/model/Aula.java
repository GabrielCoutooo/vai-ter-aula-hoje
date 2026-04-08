package main.java.com.vaiteraula.model;

public class Aula {
    private int id;
    private int turmaId;
    private int professorId;
    private String nomeDisciplina;
    private String diaSemana;
    private String horarioInicio;
    private String status; // normal ou canelada

    public Aula(){

    }
    public Aula(int id,int turmaId,int professorId,String nomeDisciplina,String diaSemana,String horarioInicio,String status){
        this.id = id;
        this.turmaId = turmaId;
        this.professorId = professorId;
        this.nomeDisciplina = nomeDisciplina;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.status = status;
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

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
