package controlador;

import controlador.factory.controladorFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.vo.Avion;
import modelo.vo.Piloto;
import modelo.vo.Usuario;
import vista.ProyectoBase;

public class controladorPiloto {
    
    // Declaración de la ventana como static, para usarla en todos los métodos
    public static ProyectoBase ventana = controladorProyectoBase.ventana;
    
    public static void insertarPiloto() {
        try {
            Avion avionActual = controladorProyectoBase.getAvionSeleccionado();
            Usuario usuarioActual = ProyectoBase.getUsuarioActual();

            if (avionActual == null) {
                JOptionPane.showMessageDialog(null,
                        "No hay ningún avión seleccionado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usuarioActual == null
                    || !usuarioActual.getListaVuelos().contains(avionActual)) {
                JOptionPane.showMessageDialog(null,
                        "No puedes asignar un piloto a un avión que no te pertenece.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dni = Integer.parseInt(ventana.getTxtDNIPiloto().getText().trim());
            Piloto pilotoExistente = controladorProyectoBase.piloto.buscarPilotoNQ(dni);
            if (pilotoExistente != null) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un piloto con este DNI.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(ventana.getTxtFechaNacimiento().getText().trim());

            Piloto nuevoPiloto = new Piloto(
                    dni,
                    fechaNacimiento,
                    ventana.getTxtNomPiloto().getText().trim(),
                    ventana.getTxtApellPiloto().getText().trim(),
                    ventana.getTxtNacionalidad().getText().trim(),
                    (int) ventana.getSpNumHijos().getValue(),
                    ventana.getCkPsicologo().isSelected(),
                    avionActual,
                    Integer.parseInt(ventana.getTxtHorasVuelo().getText().trim()),
                    ventana.getCkMilitar().isSelected()
            );

            avionActual.getListaPilotos().add(nuevoPiloto);

            controladorProyectoBase.piloto.insertarOModificar(nuevoPiloto);
            controladorProyectoBase.avion.insertarOModificar(avionActual);

            JOptionPane.showMessageDialog(null,
                    "Piloto insertado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al insertar piloto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void modificarPiloto() {
        try {
            Avion avionActual = controladorProyectoBase.getAvionSeleccionado();
            Usuario usuarioActual = ProyectoBase.getUsuarioActual();

            if (avionActual == null || usuarioActual == null) {
                JOptionPane.showMessageDialog(null,
                        "No hay avión o usuario en la sesión.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dni = Integer.parseInt(ventana.getTxtDNIPiloto().getText().trim());
            Piloto pilotoEncontrado = controladorProyectoBase.piloto.buscarPilotoNQ(dni);
            if (pilotoEncontrado == null) {
                JOptionPane.showMessageDialog(null,
                        "No existe ningún piloto con ese DNI.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Comprobar permisos de modificación
            if (!usuarioActual.getListaVuelos().contains(pilotoEncontrado.getAvion())) {
                JOptionPane.showMessageDialog(null,
                        "No puedes modificar un piloto que pertenece a otro usuario.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar datos básicos del piloto
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = sdf.parse(ventana.getTxtFechaNacimiento().getText().trim());

            pilotoEncontrado.setFecha_nacimiento(fechaNac);
            pilotoEncontrado.setNombre(ventana.getTxtNomPiloto().getText().trim());
            pilotoEncontrado.setApellido(ventana.getTxtApellPiloto().getText().trim());
            pilotoEncontrado.setNacionalidad(ventana.getTxtNacionalidad().getText().trim());
            pilotoEncontrado.setHijos((int) ventana.getSpNumHijos().getValue());
            pilotoEncontrado.setPsicologo(ventana.getCkPsicologo().isSelected());
            pilotoEncontrado.setMilitar(ventana.getCkMilitar().isSelected());
            pilotoEncontrado.setH_vuelo(
                    Integer.parseInt(ventana.getTxtHorasVuelo().getText().trim())
            );

            // Preguntar si se desea cambiar de avión
            int respuesta = JOptionPane.showConfirmDialog(null,
                    "¿Deseas cambiar el piloto a otro avión?",
                    "Cambio de avión",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                String numAvionStr = JOptionPane.showInputDialog(
                        "Introduce el número de vuelo del avión destino:");
                if (numAvionStr != null && !numAvionStr.isEmpty()) {
                    try {
                        int numAvionNuevo = Integer.parseInt(numAvionStr);
                        // Buscar ese avión
                        Avion avionNuevo = controladorProyectoBase.avion.buscarAvionSODA(numAvionNuevo);
                        if (avionNuevo == null) {
                            JOptionPane.showMessageDialog(null,
                                    "No existe ningún avión con ese número.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (!usuarioActual.getListaVuelos().contains(avionNuevo)) {
                            JOptionPane.showMessageDialog(null,
                                    "El avión " + numAvionNuevo + " no pertenece al usuario actual.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Mover el piloto: quitarlo de la lista del avión anterior y añadirlo al nuevo
                            Avion avionAnterior = pilotoEncontrado.getAvion();
                            avionAnterior.getListaPilotos().remove(pilotoEncontrado);
                            controladorProyectoBase.avion.insertarOModificar(avionAnterior);

                            pilotoEncontrado.setAvion(avionNuevo);
                            avionNuevo.getListaPilotos().add(pilotoEncontrado);
                            controladorProyectoBase.avion.insertarOModificar(avionNuevo);

                            JOptionPane.showMessageDialog(null,
                                    "Piloto movido al avión " + numAvionNuevo,
                                    "Operación exitosa",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,
                                "El número de avión introducido no es válido.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            // Guardar los cambios definitivos del piloto
            controladorProyectoBase.piloto.insertarOModificar(pilotoEncontrado);

            JOptionPane.showMessageDialog(null,
                    "Piloto modificado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar piloto: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void borrarPiloto() {
        try {
            int dni = Integer.parseInt(ventana.getTxtDNIPiloto().getText().trim());
            Piloto pilotoEncontrado = controladorProyectoBase.piloto.buscarPilotoNQ(dni);

            if (pilotoEncontrado == null) {
                JOptionPane.showMessageDialog(null,
                        "No existe ningún piloto con ese DNI.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Avion avionAsignado = pilotoEncontrado.getAvion();
            if (avionAsignado != null) {
                avionAsignado.getListaPilotos().remove(pilotoEncontrado);
                controladorProyectoBase.avion.insertarOModificar(avionAsignado);
            }

            controladorFactory.getBD().delete(pilotoEncontrado);
            controladorFactory.getBD().commit();

            JOptionPane.showMessageDialog(null,
                    "Piloto borrado correctamente.",
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al borrar piloto: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void verPilotosporAvion() {
        Avion avionSeleccionado = (Avion) ventana.getCbAvion().getSelectedItem();
        if (avionSeleccionado == null) {
            JOptionPane.showMessageDialog(null,
                    "No se ha seleccionado ningún avión",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Piloto> listaPilotos = avionSeleccionado.getListaPilotos();
        if (listaPilotos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Este avión no tiene ningún piloto asignado",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pilotos del avión: ")
                .append(avionSeleccionado.toString())
                .append("\n\n");
        for (Piloto p : listaPilotos) {
            sb.append("DNI: ").append(p.getDni())
                    .append(" - Nombre: ").append(p.getNombre())
                    .append(" ").append(p.getApellido())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null,
                sb.toString(),
                "Listado de Pilotos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void cargarPiloto() {
        try {
            int dni = Integer.parseInt(ventana.getTxtDNIPiloto().getText().trim());
            Piloto pilotoEncontrado = controladorProyectoBase.piloto.buscarPilotoNQ(dni);
            if (pilotoEncontrado == null) {
                System.out.println("No existe ningún piloto con ese DNI");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ventana.getTxtNomPiloto().setText(pilotoEncontrado.getNombre());
            ventana.getTxtApellPiloto().setText(pilotoEncontrado.getApellido());
            ventana.getTxtFechaNacimiento().setText(
                    sdf.format(pilotoEncontrado.getFecha_nacimiento()));
            ventana.getTxtNacionalidad().setText(pilotoEncontrado.getNacionalidad());
            ventana.getSpNumHijos().setValue(pilotoEncontrado.getHijos());
            ventana.getCkPsicologo().setSelected(pilotoEncontrado.isPsicologo());
            ventana.getCkMilitar().setSelected(pilotoEncontrado.isMilitar());
            ventana.getTxtHorasVuelo().setText(
                    String.valueOf(pilotoEncontrado.getH_vuelo()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar piloto: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void mostrarAvion(Avion avionActual) {
        if (avionActual == null) {
            ventana.getTxtAvionNombre().setText("Avión no seleccionado");
        } else {
            ventana.getTxtAvionNombre().setText("Hola " + avionActual.toString());
        }
    }
}
