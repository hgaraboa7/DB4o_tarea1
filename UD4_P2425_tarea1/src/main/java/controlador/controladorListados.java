package controlador;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import controlador.factory.controladorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Avion;
import modelo.vo.Piloto;
import modelo.vo.Usuario;

public class controladorListados {

    // ------------------------------------------------------------------------
    // LISTADO 1: Pilotos (SODA) cuyas horasVuelo > param1, militar == param2,
    //            y cuyo Avion pertenezca a userActual.
    //            Luego, muestra la Suma total de horas de vuelo.
    // ------------------------------------------------------------------------
    private static List<Piloto> obtenerPilotosHorasMilitar_SODA(
            int horasMin, boolean esMilitar, Usuario userActual, AtomicInteger sumaHoras) {
        Query q = controladorFactory.getBD().query();
        q.constrain(Piloto.class);

        q.descend("h_vuelo").constrain(horasMin).greater();
        q.descend("militar").constrain(esMilitar);

        q.descend("avion").descend("usuario").constrain(userActual);

        ObjectSet<Piloto> result = q.execute();
        List<Piloto> lista = new ArrayList<>(result);

        int total = 0;
        for (Piloto p : lista) {
            total += p.getH_vuelo();
        }
        sumaHoras.set(total);

        return lista;
    }

    public static void ejecutarListado1(
            String param1, String param2, JTable tablaListados, Usuario userActual) {
        try {
            if (param1.isEmpty() || param2.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Debes introducir ambos parámetros (horasMin y militar).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int horasMin = Integer.parseInt(param1);

            boolean esMilitar;
            if (param2.equalsIgnoreCase("true")) {
                esMilitar = true;
            } else if (param2.equalsIgnoreCase("false")) {
                esMilitar = false;
            } else {
                JOptionPane.showMessageDialog(null,
                        "El segundo parámetro debe ser 'true' o 'false' (militar).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            AtomicInteger sumaHoras = new AtomicInteger(0);
            List<Piloto> lista = obtenerPilotosHorasMilitar_SODA(horasMin, esMilitar, userActual, sumaHoras);

            cargarTablaPilotos(lista, tablaListados);

            JOptionPane.showMessageDialog(null,
                    "Suma total de horas de vuelo = " + sumaHoras.get(),
                    "Resultado Listado 1",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "El primer parametro (horasMin) debe ser un número.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en ejecutarListado1: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------
    // LISTADO 2: Aviones (NQ) con num_pasajeros > param1 y destino == param2,
    //            que pertenecen al userActual.
    //            Luego, muestra el Promedio de pasajeros de esos aviones.
    // ------------------------------------------------------------------------
    private static List<Avion> obtenerAvionesPasajerosDestino_NQ(
            int pasajerosMin, String destinoParam, Usuario userActual, AtomicInteger promedio) {
        List<Avion> lista = controladorFactory.getBD().query(new Predicate<Avion>() {
            @Override
            public boolean match(Avion av) {
                return av.getUsuario().equals(userActual)
                        && av.getNum_pasajeros() > pasajerosMin
                        && av.getDestino().equalsIgnoreCase(destinoParam);
            }
        });

        if (!lista.isEmpty()) {
            int suma = 0;
            for (Avion av : lista) {
                suma += av.getNum_pasajeros();
            }
            promedio.set(suma / lista.size());
        } else {
            promedio.set(0);
        }

        return lista;
    }

    public static void ejecutarListado2(
            String param1, String param2, JTable tablaListados, Usuario userActual) {
        try {
            if (param1.isEmpty() || param2.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Debes introducir ambos parametros (pasajerosMin y destino).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int pasajerosMin = Integer.parseInt(param1);
            String destinoParam = param2;

            AtomicInteger promedio = new AtomicInteger(0);
            List<Avion> lista = obtenerAvionesPasajerosDestino_NQ(pasajerosMin, destinoParam, userActual, promedio);

            cargarTablaAviones(lista, tablaListados);

            JOptionPane.showMessageDialog(null,
                    "Promedio de pasajeros= " + promedio.get(),
                    "Resultado Listado 2",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "El primer parametro (pasajerosMin) debe ser un número.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en ejecutarListado2: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------
    // LISTADO 3: Pilotos (SODA) con nacionalidad = param1 y psicologo == param2,
    //            y cuyo Avion pertenece al userActual.
    //            Luego, muestra el total de hijos de esos pilotos.
    // ------------------------------------------------------------------------
    private static List<Piloto> obtenerPilotosNacionalidadPsicologo_SODA(
            String nacionalidad, boolean esPsicologo, Usuario userActual, AtomicInteger totalHijos) {
        Query q = controladorFactory.getBD().query();
        q.constrain(Piloto.class);

        q.descend("nacionalidad").constrain(nacionalidad);
        q.descend("psicologo").constrain(esPsicologo);

        q.descend("avion").descend("usuario").constrain(userActual);

        ObjectSet<Piloto> result = q.execute();
        List<Piloto> lista = new ArrayList<>(result);

        int suma = 0;
        for (Piloto p : lista) {
            suma += p.getHijos();
        }
        totalHijos.set(suma);

        return lista;
    }

    public static void ejecutarListado3(
            String param1, String param2, JTable tablaListados, Usuario userActual) {
        try {
            if (param1.isEmpty() || param2.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Debes introducir ambos parámetros (nacionalidad y psicologo).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean esPsicologo;
            if (param2.equalsIgnoreCase("true")) {
                esPsicologo = true;
            } else if (param2.equalsIgnoreCase("false")) {
                esPsicologo = false;
            } else {
                JOptionPane.showMessageDialog(null,
                        "El segundo parámetro debe ser 'true' o 'false' (psicologo).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            AtomicInteger totalHijos = new AtomicInteger(0);
            List<Piloto> lista = obtenerPilotosNacionalidadPsicologo_SODA(
                    param1, esPsicologo, userActual, totalHijos);

            cargarTablaPilotos(lista, tablaListados);

            JOptionPane.showMessageDialog(null,
                    "Total de hijos entre tus pilotos: " + totalHijos.get(),
                    "Resultado Listado 3",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en ejecutarListado3: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------
    // Métodos de utilidad para cargar datos en la tabla de la interfaz
    // ------------------------------------------------------------------------
    private static void cargarTablaPilotos(List<Piloto> lista, JTable tablaListados) {
        String[] columnas = {"DNI", "Nombre", "Apellido", "Nacionalidad", "Hijos", "Psicologo"};

        String[][] filas = new String[lista.size()][columnas.length];
        for (int i = 0; i < lista.size(); i++) {
            Piloto p = lista.get(i);
            filas[i][0] = String.valueOf(p.getDni());
            filas[i][1] = p.getNombre();
            filas[i][2] = p.getApellido();
            filas[i][3] = p.getNacionalidad();
            filas[i][4] = String.valueOf(p.getHijos());
            filas[i][5] = String.valueOf(p.isPsicologo());
        }
        DefaultTableModel model = new DefaultTableModel(filas, columnas);
        tablaListados.setModel(model);
    }

    private static void cargarTablaAviones(List<Avion> lista, JTable tablaListados) {
        String[] columnas = {"NºVuelo", "Pasajeros", "Origen", "Destino"};

        String[][] filas = new String[lista.size()][columnas.length];
        for (int i = 0; i < lista.size(); i++) {
            Avion av = lista.get(i);
            filas[i][0] = String.valueOf(av.getNum_vuelo());
            filas[i][1] = String.valueOf(av.getNum_pasajeros());
            filas[i][2] = av.getOrigen();
            filas[i][3] = av.getDestino();
        }
        DefaultTableModel model = new DefaultTableModel(filas, columnas);
        tablaListados.setModel(model);
    }
}
