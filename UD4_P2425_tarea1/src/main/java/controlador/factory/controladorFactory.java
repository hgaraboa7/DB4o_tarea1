/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.factory;
//
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import modelo.dao.AvionDAO;
import modelo.dao.PilotoDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.Avion;
import modelo.vo.Piloto;
import modelo.vo.Usuario;


/**
 *
 * @author Acceso a datos
 */
public class controladorFactory {
    static String BDEmpresa = "./src/main/java/BD/ejemplo.db40";
    static ObjectContainer bd;

  public static void abrirBD() {
    EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();

      config.common().objectClass(Avion.class).cascadeOnUpdate(true);
  
     config.common().objectClass(Usuario.class).cascadeOnUpdate(true);
  
     config.common().objectClass(Piloto.class).cascadeOnUpdate(true);
   
    bd = Db4oEmbedded.openFile(config, BDEmpresa);
}


    public static void cerrarBD() {
        bd.close();
    }

    public static ObjectContainer getBD() {
        return bd;
    }
    
    public static PilotoDAO getPasajeroDAO() {
        return new PilotoDAO();
    }
     public static AvionDAO getAvionDAO() {
        return new AvionDAO();
    }
      public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO();
    }
    
    
}
