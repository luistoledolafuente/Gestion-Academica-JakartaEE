package org.example.sem5.model;

import java.sql.Timestamp;

public class Matricula {
    private int idMatricula;
    private int idAlumno; // Llave foránea a Alumno
    private int idPeriodo; // Llave foránea a PeriodoAcademico
    private Timestamp fechaMatricula;
    private String estado; // Corresponde al ENUM('activo', 'retirado', 'anulado')

    public Matricula() {
    }

    public Matricula(int idMatricula, int idAlumno, int idPeriodo, Timestamp fechaMatricula, String estado) {
        this.idMatricula = idMatricula;
        this.idAlumno = idAlumno;
        this.idPeriodo = idPeriodo;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Timestamp getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Timestamp fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}