package modelo.dao;

import org.mindrot.jbcrypt.BCrypt;
import com.db4o.ObjectSet;
import modelo.vo.Usuario;
import controlador.factory.controladorFactory;

public class UsuarioDAO {

    public Usuario buscarQBE(String nombre) {
        Usuario usuarioEjemplo = new Usuario(nombre);
        ObjectSet result = controladorFactory.getBD().queryByExample(usuarioEjemplo);
        if (result.hasNext()) {
            return (Usuario) result.next();
        } else {
            return null;
        }
    }

    public void insertar(Usuario usuario) {
        
        controladorFactory.getBD().store(usuario);
        controladorFactory.getBD().commit();
    }

    public void modificar(Usuario usuario) {
        controladorFactory.getBD().store(usuario);
    }

    public boolean autenticarUsuario(String nombre, String pass) {
        Usuario usuario = buscarQBE(nombre);
        if (usuario != null) {
            
            usuario.getPass();
            return usuario.verificarPassword(pass);
        }
        return false;
    }
}
