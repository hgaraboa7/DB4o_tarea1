package modelo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Avion implements Serializable {
    
    private int num_vuelo;
    private int num_pasajeros;
    private Date fecha_vuelo;
    private String origen;
    private String destino;
    private String detalles;
    private Usuario usuario;
    private List<Piloto> listaPilotos;

    public Avion(int num_vuelo, int num_pasajeros, Date fecha_vuelo, String origen, String destino, Usuario usuario) {
        this.num_vuelo = num_vuelo;
        this.num_pasajeros = num_pasajeros;
        this.fecha_vuelo = fecha_vuelo;
        this.origen = origen;
        this.destino = destino;
        this.listaPilotos = new ArrayList<>();
        this.usuario = usuario;
    }

    public Avion() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Piloto> getListaPilotos() {
        return listaPilotos;
    }

    public void setListaPilotos(List<Piloto> listaPilotos) {
        this.listaPilotos = listaPilotos;
    }

    public int getNum_vuelo() {
        return num_vuelo;
    }

    public void setNum_vuelo(int num_vuelo) {
        this.num_vuelo = num_vuelo;
    }

    public int getNum_pasajeros() {
        return num_pasajeros;
    }

    public void setNum_pasajeros(int num_pasajeros) {
        this.num_pasajeros = num_pasajeros;
    }

    public Date getFecha_vuelo() {
        return fecha_vuelo;
    }

    public void setFecha_vuelo(Date fecha_vuelo) {
        this.fecha_vuelo = fecha_vuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return this.num_vuelo == avion.num_vuelo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(num_vuelo);
    }

    @Override
    public String toString() {
        return String.valueOf(num_vuelo + " O-" + origen + " D-" + destino);
    }
}
