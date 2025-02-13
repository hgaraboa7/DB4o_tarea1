/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hector.garaboacasas
 */
public class Piloto implements Serializable {

    private Avion avion;

    private int dni;
    private Date fecha_nacimiento;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private int hijos;
    private boolean psicologo;
    private int h_vuelo;
    private boolean militar;

    public Piloto(int dni,Date fecha_nacimiento, String nombre, String apellido, String nacionalidad, int hijos, boolean psicologo,Avion avion, int h_vuelo, boolean militar) {
        this.dni=dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.hijos = hijos;
        this.psicologo = psicologo;
        this.avion = avion;
        this.h_vuelo = h_vuelo;
        this.militar = militar;
    }

    public Piloto() {
    }

    public int getH_vuelo() {
        return h_vuelo;
    }

    public void setH_vuelo(int h_vuelo) {
        this.h_vuelo = h_vuelo;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }    

    @Override
    public String toString() {
        return nombre + ", " + apellido;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getHijos() {
        return hijos;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public boolean isPsicologo() {
        return psicologo;
    }

    public boolean isMilitar() {
        return militar;
    }

    public void setMilitar(boolean militar) {
        this.militar = militar;
    }

    public void setPsicologo(boolean psicologo) {
        this.psicologo = psicologo;
    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Piloto piloto = (Piloto) o;
    return dni == piloto.dni;
}

@Override
public int hashCode() {
    return Integer.hashCode(dni);
}

}
