package controlador;

import controlador.factory.controladorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.vo.Avion;
import modelo.vo.Usuario;
import vista.ProyectoBase;

public class controladorAvion {

    public static void insertarVuelo(Usuario usuarioActual) {
        try {
             ProyectoBase ventana = controladorProyectoBase.ventana;

            ArrayList<Avion> listaAviones = 
                new ArrayList<>(usuarioActual.getListaVuelos());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha_vuelo = sdf.parse(ventana.getTxtFecha().getText());

            int numVuelo = Integer.parseInt(ventana.getTxtNumvuelo().getText().trim());
            Avion avion1 = controladorProyectoBase.avion.buscarAvionSODA(numVuelo);

            if (avion1 == null) {
                  avion1 = new Avion(numVuelo,
                        Integer.parseInt(ventana.getTxtNPasajeros().getText().trim()),
                        fecha_vuelo,
                        ventana.getCbOrigenVuelo().getSelectedItem().toString(),
                        ventana.getCbDestinoVuelo().getSelectedItem().toString(),
                        usuarioActual);

                 avion1.setDetalles(ventana.getTaDetallesVuelo().getText());

                 if (!listaAviones.contains(avion1)) {
                    listaAviones.add(avion1);
                }
                usuarioActual.setListaVuelos(listaAviones);

                controladorProyectoBase.avion.insertarOModificar(avion1);
                controladorProyectoBase.user.modificar(usuarioActual);

                 cargarComboAvion(usuarioActual);

                JOptionPane.showMessageDialog(null,
                        "insertado correctamente",
                        "Inserción de Avión",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un avión con el número de vuelo " + numVuelo,
                        "Error de inserción",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en formato de fecha (use yyyy-MM-dd HH:mm:ss)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "El número de vuelo o pasajeros es inválido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex1) {
            JOptionPane.showMessageDialog(null, 
                "Hubo un error: " + ex1.getMessage());
            ex1.printStackTrace();
        }
    }

    public static void cargarComboAvion(Usuario usuarioActual) {
        ProyectoBase ventana = controladorProyectoBase.ventana;
        controladorProyectoBase.modeloAvion.removeAllElements();
        controladorProyectoBase.modeloAvion.addAll(usuarioActual.getListaVuelos());

        if (!usuarioActual.getListaVuelos().isEmpty()) {
            ventana.getCbAvion().setSelectedIndex(0);
        }
    }

    public static void cargarDatosAvion() {
        ProyectoBase ventana = controladorProyectoBase.ventana;
        Avion avion1 = (Avion) ventana.getCbAvion().getSelectedItem();
        if (avion1 == null) {
            ventana.getTxtNumvuelo().setText("");
            ventana.getTxtNPasajeros().setText("");
            ventana.getTxtFecha().setText("");
            ventana.getTaDetallesVuelo().setText("");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha_vuelo = sdf.format(avion1.getFecha_vuelo());

        ventana.getTxtNumvuelo().setText(String.valueOf(avion1.getNum_vuelo()));
        ventana.getTxtNPasajeros().setText(String.valueOf(avion1.getNum_pasajeros()));
        ventana.getTxtFecha().setText(fecha_vuelo);
        ventana.getCbOrigenVuelo().setSelectedItem(avion1.getOrigen());
        ventana.getCbDestinoVuelo().setSelectedItem(avion1.getDestino());
        ventana.getTaDetallesVuelo().setText(
            avion1.getDetalles() == null ? "" : avion1.getDetalles()
        );
        ventana.getTxtNumvuelo().setEnabled(false);
    }

    public static void modificarAvion(Usuario usuarioActual) {
        ProyectoBase ventana = controladorProyectoBase.ventana;
        try {
            ArrayList<Avion> listaAviones = 
                new ArrayList<>(usuarioActual.getListaVuelos());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha_vuelo = sdf.parse(ventana.getTxtFecha().getText().trim());

            int numVuelo = Integer.parseInt(ventana.getTxtNumvuelo().getText().trim());
            Avion avion1 = controladorProyectoBase.avion.buscarAvionSODA(numVuelo);

            if (avion1 != null) {
                avion1.setNum_pasajeros(
                    Integer.parseInt(ventana.getTxtNPasajeros().getText().trim()));
                avion1.setFecha_vuelo(fecha_vuelo);
                avion1.setOrigen(ventana.getCbOrigenVuelo().getSelectedItem().toString());
                avion1.setDestino(ventana.getCbDestinoVuelo().getSelectedItem().toString());
                avion1.setDetalles(ventana.getTaDetallesVuelo().getText());

                 if (!listaAviones.contains(avion1)) {
                    listaAviones.add(avion1);
                }
                usuarioActual.setListaVuelos(listaAviones);

                controladorProyectoBase.avion.insertarOModificar(avion1);
                controladorProyectoBase.user.modificar(usuarioActual);

                cargarComboAvion(usuarioActual);

                JOptionPane.showMessageDialog(null,
                        "Avión modificado correctamente",
                        "Modificación de Avión",
                        JOptionPane.INFORMATION_MESSAGE);

                ventana.getTxtNumvuelo().setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null,
                        "No existe el avión con nº vuelo " + numVuelo,
                        "Error al modificar",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en formato de fecha (use yyyy-MM-dd HH:mm:ss)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "El número de vuelo o pasajeros no es válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error desconocido al modificar avión: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void prepararNuevoAvion() {
        controladorProyectoBase.ventana.getCbAvion().setSelectedIndex(-1);
        controladorProyectoBase.limpiarCampos();
    }

  public static void borrarAvion() {
    ProyectoBase ventana = controladorProyectoBase.ventana;
    Avion avionSeleccionado = (Avion) ventana.getCbAvion().getSelectedItem();
    Usuario usuarioActual = ProyectoBase.getUsuarioActual();
    
    if (avionSeleccionado == null) {
        JOptionPane.showMessageDialog(null,
                "No hay ningún avión seleccionado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (usuarioActual == null || !usuarioActual.getListaVuelos().contains(avionSeleccionado)) {
        JOptionPane.showMessageDialog(null,
                "No puedes borrar un avión que no te pertenece.",
                "Acceso denegado",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    // NUEVO: Comprobar si el avión tiene pilotos
    if (!avionSeleccionado.getListaPilotos().isEmpty()) {
        JOptionPane.showMessageDialog(null,
                "No se puede borrar este avión porque tiene pilotos asociados.",
                "Borrado restringido",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Si no tiene pilotos, se borra el avión
    usuarioActual.getListaVuelos().remove(avionSeleccionado);
    controladorProyectoBase.user.modificar(usuarioActual);  // Actualiza la lista en el usuario

    controladorFactory.getBD().delete(avionSeleccionado);
    controladorFactory.getBD().commit();

    JOptionPane.showMessageDialog(null,
            "Avión borrado correctamente.",
            "Operación exitosa",
            JOptionPane.INFORMATION_MESSAGE);

    // Tras borrarlo, refrescar el combo u otros componentes
    cargarComboAvion(usuarioActual);
    controladorProyectoBase.limpiarCampos();
}
}
