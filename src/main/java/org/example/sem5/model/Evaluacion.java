package org.example.sem5.model;

import java.math.BigDecimal;

public class Evaluacion {
    private int idEvaluacion;
    private int idCurso; // Llave foránea a Curso
    private String nombre;
    private BigDecimal peso; // Usamos BigDecimal para precisión decimal (ej: 20.50)

    public Evaluacion() {
    }

    public Evaluacion(int idEvaluacion, int idCurso, String nombre, BigDecimal peso) {
        this.idEvaluacion = idEvaluacion;
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.peso = peso;
    }

    // Getters y Setters
    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
}