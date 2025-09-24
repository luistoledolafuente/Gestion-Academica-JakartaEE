package org.example.sem5.model;

import java.math.BigDecimal;

public class Nota {
    private int idNota;
    private int idDetalle;    // Llave foránea a DetalleMatricula
    private int idEvaluacion; // Llave foránea a Evaluacion
    private BigDecimal nota;  // Usamos BigDecimal para precisión en las calificaciones

    public Nota() {
    }

    public Nota(int idNota, int idDetalle, int idEvaluacion, BigDecimal nota) {
        this.idNota = idNota;
        this.idDetalle = idDetalle;
        this.idEvaluacion = idEvaluacion;
        this.nota = nota;
    }

    // Getters y Setters
    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
}