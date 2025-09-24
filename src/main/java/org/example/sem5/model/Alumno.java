package org.example.sem5.model;

import java.sql.Timestamp;

public class Alumno {
    private int idAlumno;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String email;
    private Timestamp fechaRegistro;

    // Constructor vacío
    public Alumno() {
    }

    // Constructor con todos los parámetros (útil para crear objetos)
    public Alumno(int idAlumno, String codigo, String nombres, String apellidos, String email, Timestamp fechaRegistro) {
        this.idAlumno = idAlumno;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}