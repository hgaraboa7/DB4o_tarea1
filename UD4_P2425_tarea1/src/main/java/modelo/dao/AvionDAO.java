package modelo.dao;

import com.db4o.ObjectSet;
import com.db4o.query.Query;
import controlador.factory.controladorFactory;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Avion;

public class AvionDAO {

    public Avion buscarAvionSODA(Integer num_vuelo) {
        Query query = controladorFactory.getBD().query();
        query.constrain(Avion.class);
        query.descend("num_vuelo").constrain(num_vuelo);
        ObjectSet<Avion> result = query.execute();
        if (result.hasNext()) {
            return result.next();
        }
        return null;
    }

    public void insertarOModificar(Avion avion) {
    Avion avionExistente = buscarAvionSODA(avion.getNum_vuelo());
    if (avionExistente != null) {
         avionExistente.setNum_pasajeros(avion.getNum_pasajeros());
        avionExistente.setFecha_vuelo(avion.getFecha_vuelo());
        avionExistente.setOrigen(avion.getOrigen());
        avionExistente.setDestino(avion.getDestino());
        avionExistente.setDetalles(avion.getDetalles());
        avionExistente.setUsuario(avion.getUsuario());

        avionExistente.setListaPilotos(avion.getListaPilotos());

        controladorFactory.getBD().store(avionExistente);
    } else {
         controladorFactory.getBD().store(avion);
    }

    controladorFactory.getBD().commit();
}
    public List<Avion> buscarTodosAviones() {
    Query query = controladorFactory.getBD().query();
    query.constrain(Avion.class);
    ObjectSet<Avion> result = query.execute();
    return new ArrayList<>(result);
}


}
