package modelo.vo;

import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    
    private String nombre;
    private String pass;
    private List<Avion> listaVuelos;

    public Usuario(String nombre, String pass) {
        this.nombre = nombre;
        this.setPass(pass);
        this.listaVuelos = new ArrayList<>();
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.listaVuelos = new ArrayList<>();
    }

    public Usuario() {
    }

    public List<Avion> getListaVuelos() {
        return listaVuelos;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  public void setPass(String pass) {
    if (!pass.startsWith("$2a$")) { // Evita re-encriptar si ya está en formato BCrypt
        this.pass = BCrypt.hashpw(pass, BCrypt.gensalt());
    } else {
        this.pass = pass;
    }
}


    public boolean verificarPassword(String pass) {
        
        
        System.out.println( BCrypt.checkpw(pass, this.pass));
        return BCrypt.checkpw(pass, this.pass);
    }

    public void setListaVuelos(List<Avion> listaVuelos) {
        this.listaVuelos = listaVuelos;
    }
}
