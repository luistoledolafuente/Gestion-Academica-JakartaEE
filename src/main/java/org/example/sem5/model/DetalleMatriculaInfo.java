package org.example.sem5.model;

public class DetalleMatriculaInfo {
    private int idDetalle;
    private String nombreCurso;
    private int creditosCurso;
    private String estadoDetalle; // matriculado, retirado

    public DetalleMatriculaInfo(int idDetalle, String nombreCurso, int creditosCurso, String estadoDetalle) {
        this.idDetalle = idDetalle;
        this.nombreCurso = nombreCurso;
        this.creditosCurso = creditosCurso;
        this.estadoDetalle = estadoDetalle;
    }
    // Genera aquí los Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    public int getCreditosCurso() { return creditosCurso; }
    public void setCreditosCurso(int creditosCurso) { this.creditosCurso = creditosCurso; }
    public String getEstadoDetalle() { return estadoDetalle; }
    public void setEstadoDetalle(String estadoDetalle) { this.estadoDetalle = estadoDetalle; }
}