package Luis.JuegoDados.model.repository;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.PartidaEntityJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepositoryJpa extends JpaRepository<PartidaEntityJpa,Long> {
    /**
     * Busca y devuelve una lista de partidas asociadas a un jugador específico.
     *
     * @param jugador El jugador para el que se desea buscar las partidas.
     * @return Una lista de objetos PartidaEntityJpa relacionados con el jugador.
     */
    List<PartidaEntityJpa> findByJugador(JugadorEntityJpa jugador);
}
