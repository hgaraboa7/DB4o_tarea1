package modelo.dao;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import controlador.factory.controladorFactory;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Avion;
import modelo.vo.Piloto;

public class PilotoDAO {

   public Piloto buscarPilotoNQ(final Integer dni) {
    List<Piloto> result = controladorFactory.getBD().query(new Predicate<Piloto>() {
        @Override
        public boolean match(Piloto piloto) {
            return piloto.getDni() == dni;
        }
    });

    if (!result.isEmpty()) {
        return result.get(0);  // Retornar el primer resultado encontrado
    } else {
        return null;  // No se encontró ningún piloto con ese DNI
    }
}


 public void insertarOModificar(Piloto piloto) {
    Piloto pilotoExistente = buscarPilotoNQ(piloto.getDni());
    if (pilotoExistente != null) {
        // Actualizar datos del piloto
        pilotoExistente.setFecha_nacimiento(piloto.getFecha_nacimiento());
        pilotoExistente.setNombre(piloto.getNombre());
        pilotoExistente.setApellido(piloto.getApellido());
        pilotoExistente.setNacionalidad(piloto.getNacionalidad());
        pilotoExistente.setHijos(piloto.getHijos());
        pilotoExistente.setPsicologo(piloto.isPsicologo());
        pilotoExistente.setH_vuelo(piloto.getH_vuelo());
        pilotoExistente.setMilitar(piloto.isMilitar());
        pilotoExistente.setAvion(piloto.getAvion());
        
        controladorFactory.getBD().store(pilotoExistente);
    } else {
        // Insertar nuevo piloto
        controladorFactory.getBD().store(piloto);
    }
    
    // Actualizar también el avión asociado
    Avion avion = piloto.getAvion();
    if (avion != null) {
        controladorFactory.getBD().store(avion);
    }

    controladorFactory.getBD().commit();
}
 
 public List<Piloto> buscarPilotosPorAvion(Avion avion) {
    Query q = controladorFactory.getBD().query();
    q.constrain(Piloto.class);
    // Para que busque los pilotos cuyo "avion" == el avion que pasamos
    q.descend("avion").constrain(avion);
    
    ObjectSet<Piloto> resultado = q.execute();
    return new ArrayList<>(resultado);
}


}
