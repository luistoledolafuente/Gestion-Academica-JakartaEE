package org.example.sem5.model;

import java.sql.Timestamp;

// Esta clase es un DTO (Data Transfer Object) o ViewModel.
// No corresponde a una tabla, sino que transporta datos combinados para la vista.
public class MatriculaInfo {

    private int idMatricula;
    private Timestamp fechaMatricula;
    private String estadoMatricula;
    private String nombreCompletoAlumno;
    private String codigoAlumno;
    private String nombrePeriodo;

    // Constructor, Getters y Setters
    public MatriculaInfo(int idMatricula, Timestamp fechaMatricula, String estadoMatricula, String nombreCompletoAlumno, String codigoAlumno, String nombrePeriodo) {
        this.idMatricula = idMatricula;
        this.fechaMatricula = fechaMatricula;
        this.estadoMatricula = estadoMatricula;
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.codigoAlumno = codigoAlumno;
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Timestamp getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Timestamp fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }
}