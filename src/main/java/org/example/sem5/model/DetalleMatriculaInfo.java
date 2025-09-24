package org.example.sem5.model;

// Esta es la clase 'ViewModel' que usamos para mostrar datos combinados en las vistas.
public class DetalleMatriculaInfo {
    private int idDetalle;
    private int idCurso;
    private String nombreCurso;
    private int creditosCurso;
    private String estadoDetalle;

    public DetalleMatriculaInfo(int idDetalle, int idCurso, String nombreCurso, int creditosCurso, String estadoDetalle) {
        this.idDetalle = idDetalle;
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.creditosCurso = creditosCurso;
        this.estadoDetalle = estadoDetalle;
    }

    // Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    public int getCreditosCurso() { return creditosCurso; }
    public void setCreditosCurso(int creditosCurso) { this.creditosCurso = creditosCurso; }
    public String getEstadoDetalle() { return estadoDetalle; }
    public void setEstadoDetalle(String estadoDetalle) { this.estadoDetalle = estadoDetalle; }
}