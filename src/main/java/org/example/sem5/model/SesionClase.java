package org.example.sem5.model;

import java.sql.Timestamp;

public class SesionClase {
    private int idSesion;
    private int idCurso;
    private int idPeriodo;
    private Timestamp fecha;
    private String tema;

    public SesionClase() {
    }

    public SesionClase(int idSesion, int idCurso, int idPeriodo, Timestamp fecha, String tema) {
        this.idSesion = idSesion;
        this.idCurso = idCurso;
        this.idPeriodo = idPeriodo;
        this.fecha = fecha;
        this.tema = tema;
    }

    // Getters y Setters
    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}