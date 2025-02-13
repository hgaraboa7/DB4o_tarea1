package controlador;

import controlador.factory.controladorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.dao.AvionDAO;
import modelo.dao.PilotoDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.Avion;
import modelo.vo.Piloto;
import modelo.vo.Usuario;
import vista.ProyectoBase;

public class controladorProyectoBase {

    public static ProyectoBase ventana = new ProyectoBase();

    public static AvionDAO avion;
    public static UsuarioDAO user;
    public static PilotoDAO piloto;

     public static DefaultComboBoxModel modeloAvion = new DefaultComboBoxModel();

    private static Avion avionSeleccionado;

    public static Avion getAvionSeleccionado() {
        return avionSeleccionado;
    }

    public static void setAvionSeleccionado(Avion avion) {
        avionSeleccionado = avion;
    }

   
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCbAvion().setModel(modeloAvion);
    }

    public static void iniciaBD() {
        controladorFactory.abrirBD();

        avion = controladorFactory.getAvionDAO();
        user = controladorFactory.getUsuarioDAO();
        piloto = controladorFactory.getPasajeroDAO();
    }

    public static void cerrarBD() {
        controladorFactory.cerrarBD();
    }

    public static void recargarRelacionesManualmente() {
        List<Avion> listaAviones = avion.buscarTodosAviones();
        for (Avion a : listaAviones) {
            List<Piloto> pilotosDeEsteAvion = piloto.buscarPilotosPorAvion(a);
            a.setListaPilotos(pilotosDeEsteAvion);
        }
    }

   public static void iniciarDatos() {
    try {
         Usuario user1 = user.buscarQBE("pepe");
        Usuario user2 = user.buscarQBE("pepe2");

        if (user1 == null) {
            user1 = new Usuario("pepe", "1234");
            user.insertar(user1);
        }
        if (user2 == null) {
            user2 = new Usuario("pepe2", "1234");
            user.insertar(user2);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha_vuelo1 = sdf.parse("2024-12-01 22:22:00");
        Date fecha_vuelo2 = sdf.parse("2024-12-02 22:22:00");
        Date fecha_vuelo3 = sdf.parse("2024-12-12 22:22:00");
        Date fecha_vuelo4 = sdf.parse("2024-12-02 22:22:00");

        Avion avion1 = avion.buscarAvionSODA(1);
        Avion avion2 = avion.buscarAvionSODA(2);
        Avion avion3 = avion.buscarAvionSODA(3);
        Avion avion4 = avion.buscarAvionSODA(4);

        if (avion1 == null) {
            avion1 = new Avion(1, 150, fecha_vuelo1,
                    "Buenos Aires", "Brasilia", user1);
        }
        if (avion2 == null) {
            avion2 = new Avion(2, 88, fecha_vuelo2,
                    "Madrid", "Berlin", user1);
        }
        if (avion3 == null) {
            avion3 = new Avion(3, 200, fecha_vuelo3,
                    "Brasilia", "Moscu", user1);
        }
        if (avion4 == null) {
            avion4 = new Avion(4, 10, fecha_vuelo4,
                    "Madrid", "Roma", user2);
        }

         avion.insertarOModificar(avion1);
        avion.insertarOModificar(avion2);
        avion.insertarOModificar(avion3);
        avion.insertarOModificar(avion4);

         List<Avion> listaAviones1 = new ArrayList<>(user1.getListaVuelos());
        List<Avion> listaAviones2 = new ArrayList<>(user2.getListaVuelos());

        if (!listaAviones1.contains(avion1)) listaAviones1.add(avion1);
        if (!listaAviones1.contains(avion2)) listaAviones1.add(avion2);
        if (!listaAviones1.contains(avion3)) listaAviones1.add(avion3);
        if (!listaAviones2.contains(avion4)) listaAviones2.add(avion4);

        user1.setListaVuelos(listaAviones1);
        user2.setListaVuelos(listaAviones2);

        user.modificar(user1);
        user.modificar(user2);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_nacimiento1 = sdf2.parse("1990-05-15");
        Date fecha_nacimiento2 = sdf2.parse("1985-08-20");
        Date fecha_nacimiento3 = sdf2.parse("2000-03-10");
        Date fecha_nacimiento4 = sdf2.parse("1993-11-25");

        Piloto[] pilotos = {
            new Piloto(5001, fecha_nacimiento1, "Alejandro", "Fernandez", "Argentina",
                    2, true, avion1, 5000, false),
            new Piloto(5002, fecha_nacimiento2, "Roberto", "Sanchez", "Brasil",
                    0, false, avion1, 6000, true),
            new Piloto(5003, fecha_nacimiento3, "Carlos", "Gomez", "España",
                    1, false, avion1, 7000, false),
            new Piloto(5004, fecha_nacimiento4, "Luis", "Martinez", "Italia",
                    3, true, avion1, 8000, true),

            new Piloto(5005, fecha_nacimiento1, "Pedro", "Lopez", "Francia",
                    1, false, avion2, 5500, false),
            new Piloto(5006, fecha_nacimiento2, "Ana", "Hernandez", "Portugal",
                    2, true, avion2, 6500, true),
            new Piloto(5007, fecha_nacimiento3, "Diego", "Ramirez", "México",
                    0, false, avion2, 7200, false),
            new Piloto(5008, fecha_nacimiento4, "Sofia", "Ortega", "Alemania",
                    1, true, avion2, 7800, true),

            new Piloto(5009, fecha_nacimiento1, "Ricardo", "Diaz", "Chile",
                    2, false, avion3, 5300, true),
            new Piloto(5010, fecha_nacimiento2, "Elena", "Vargas", "Perú",
                    0, true, avion3, 6200, false),
            new Piloto(5011, fecha_nacimiento3, "Hugo", "Castro", "Ecuador",
                    1, false, avion3, 7300, true),
            new Piloto(5012, fecha_nacimiento4, "Valeria", "Reyes", "Colombia",
                    3, true, avion3, 8100, false),

            new Piloto(5013, fecha_nacimiento1, "Luis", "Mendoza", "Uruguay",
                    1, false, avion4, 5600, true),
            new Piloto(5014, fecha_nacimiento2, "Isabel", "Ortiz", "Bolivia",
                    2, true, avion4, 6400, false),
            new Piloto(5015, fecha_nacimiento3, "Javier", "Santos", "Paraguay",
                    0, false, avion4, 7100, true),
            new Piloto(5016, fecha_nacimiento4, "Camila", "Flores", "Venezuela",
                    1, true, avion4, 7500, false)
        };

         for (Piloto p : pilotos) {
            piloto.insertarOModificar(p);

            if (!p.getAvion().getListaPilotos().contains(p)) {
                p.getAvion().getListaPilotos().add(p);
            }
        }

         avion.insertarOModificar(avion1);
        avion.insertarOModificar(avion2);
        avion.insertarOModificar(avion3);
        avion.insertarOModificar(avion4);

        controladorFactory.getBD().commit();

    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Hubo un error de fecha");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Formato incorrecto en la entrada de datos");
    } catch (Exception ex1) {
        Logger.getLogger(controladorProyectoBase.class.getName()).log(Level.SEVERE, null, ex1);
    }
}


     

    public static void saludar(Usuario usuarioActual) {
        ventana.getLbSaludo().setText("Hola " + usuarioActual.toString());
        
    }

 
    public static Usuario autenticar() {
        if (ventana.getTxtNombreUser().getText().isBlank() 
            || ventana.getTxtPass().getPassword().length == 0) 
        {
            JOptionPane.showMessageDialog(null, 
                "El nombre de usuario o la contraseña están vacíos.");
            return null;
        }

        String password = new String(ventana.getTxtPass().getPassword());

        if (user.autenticarUsuario(ventana.getTxtNombreUser().getText(), password)) {
            Usuario usuario = user.buscarQBE(ventana.getTxtNombreUser().getText());

            controladorAvion.cargarComboAvion(usuario);

            ventana.getFrameIniciado().setVisible(true);
            ventana.getFrameIniciado().setSize(950, 950);

            return usuario;
        } else {
            JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos");
            return null;
        }
    }

  
    public static void limpiarCampos() {
        // Avion
        ventana.getTxtNumvuelo().setText("");
        ventana.getTxtNumvuelo().setEnabled(true);
        ventana.getTxtNPasajeros().setText("");
        ventana.getTxtFecha().setText("2025-12-12 22:22:22");
        ventana.getTaDetallesVuelo().setText("");
        ventana.getCbOrigenVuelo().setSelectedIndex(-1);
        ventana.getCbDestinoVuelo().setSelectedIndex(-1);

        // Piloto
        ventana.getTxtDNIPiloto().setText("");
        ventana.getTxtNomPiloto().setText("");
        ventana.getTxtApellPiloto().setText("");
        ventana.getTxtFechaNacimiento().setText("2025-12-12");
        ventana.getTxtNacionalidad().setText("");
        ventana.getSpNumHijos().setValue(0);
        ventana.getCkPsicologo().setSelected(false);
        ventana.getCkMilitar().setSelected(false);
        ventana.getTxtHorasVuelo().setText("");

        ventana.getTxtAvionNombre().setText("Avion ");
    }

    public static void prepararNuevoAvion() {
        ventana.getCbAvion().setSelectedIndex(-1);
        limpiarCampos();
    }

    public static void darAlta() {
  
      String nuevoNombre = ventana.getTxtGestionNombre().getText().trim();
    String nuevaPass = new String(ventana.getTxtGestionPass().getPassword()).trim();

    if (nuevoNombre.isEmpty() || nuevaPass.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nombre/contraseña no pueden estar vacíos.");
        return;
    }
    
    // Comprobar si existe ya un usuario con ese nombre
    Usuario uExistente = user.buscarQBE(nuevoNombre);
    if (uExistente != null) {
        JOptionPane.showMessageDialog(null,
                "Ya existe un usuario con ese nombre, no se puede realizar el alta.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Si no existe, damos de alta
    Usuario nuevo = new Usuario(nuevoNombre, nuevaPass);
    user.insertar(nuevo);
    JOptionPane.showMessageDialog(null, "Usuario " + nuevoNombre + " dado de alta correctamente.");
}

    public static void darBaja() {
   
    if (ProyectoBase.getUsuarioActual() == null) {
        JOptionPane.showMessageDialog(null, 
            "No hay ningún usuario logueado para dar de baja.",
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    Usuario usuarioABorrar = ProyectoBase.getUsuarioActual();
    String contrasenaIntroducida = JOptionPane.showInputDialog(
        "Introduce la contraseña de " + usuarioABorrar.getNombre() + " para confirmar la baja:");

    if (contrasenaIntroducida == null || contrasenaIntroducida.isEmpty()) {
        JOptionPane.showMessageDialog(null,
            "Operación cancelada o contraseña no válida.",
            "Baja de usuario",
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    // Verificar la contraseña
    if (!usuarioABorrar.verificarPassword(contrasenaIntroducida)) {
        JOptionPane.showMessageDialog(null,
            "Contraseña incorrecta. No se procederá con la baja.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Si la contraseña es correcta, borramos "en cascada"
    // 1. Recuperar todos los aviones del usuario
    List<Avion> avionesDelUsuario = new ArrayList<>(usuarioABorrar.getListaVuelos());

    for (Avion av : avionesDelUsuario) {
        // 2. Borrar todos los pilotos asociados a cada avión
        List<Piloto> pilotosDelAvion = new ArrayList<>(av.getListaPilotos());
        for (Piloto p : pilotosDelAvion) {
            controladorFactory.getBD().delete(p);
        }
        // 3. Borrar el propio avión
        controladorFactory.getBD().delete(av);
    }

    // 4. Borrar el usuario
    controladorFactory.getBD().delete(usuarioABorrar);
    controladorFactory.getBD().commit();

    // 5. Cerrar la sesión
    ProyectoBase.setUsuarioActual(null);
    ventana.getFrameIniciado().setVisible(false);

    JOptionPane.showMessageDialog(null,
        "Usuario y sus datos borrados correctamente.",
        "Baja de usuario",
        JOptionPane.INFORMATION_MESSAGE);
}

}
