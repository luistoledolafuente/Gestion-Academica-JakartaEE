package org.example.sem5.model;

import java.math.BigDecimal;

public class NotaInfo {
    private String nombreEvaluacion;
    private BigDecimal pesoEvaluacion;
    private BigDecimal notaObtenida;

    public NotaInfo(String nombreEvaluacion, BigDecimal pesoEvaluacion, BigDecimal notaObtenida) {
        this.nombreEvaluacion = nombreEvaluacion;
        this.pesoEvaluacion = pesoEvaluacion;
        this.notaObtenida = notaObtenida;
    }
    // Genera aquí los Getters y Setters
    public String getNombreEvaluacion() { return nombreEvaluacion; }
    public void setNombreEvaluacion(String nombreEvaluacion) { this.nombreEvaluacion = nombreEvaluacion; }
    public BigDecimal getPesoEvaluacion() { return pesoEvaluacion; }
    public void setPesoEvaluacion(BigDecimal pesoEvaluacion) { this.pesoEvaluacion = pesoEvaluacion; }
    public BigDecimal getNotaObtenida() { return notaObtenida; }
    public void setNotaObtenida(BigDecimal notaObtenida) { this.notaObtenida = notaObtenida; }
}