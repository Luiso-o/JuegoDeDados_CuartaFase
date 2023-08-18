package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.dto.PartidaDtoJpa;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.PartidaEntityJpa;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import Luis.JuegoDados.model.repository.PartidaRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Service
public class PartidaServiceJpa {

    private PartidaRepositoryJpa partidaRepositoryJpa;

    private JugadorRepositoryJpa jugadorRepositoryJpa;

    /**
     * Crea una nueva partida para un jugador.
     *
     * @param jugador Jugador para el cual se creará la partida.
     * @return Objeto PartidaDtoJpa que representa la partida creada.
     */
    public PartidaDtoJpa crearPartida(JugadorEntityJpa jugador){
        int lanzada = tirarDados();
        PartidaEntityJpa nuevaPartida = PartidaEntityJpa.builder()
                .fecha(LocalDate.now())
                .victorias(lanzada <= 7 ? 1 : 0)
                .derrotas(lanzada > 7 ? 1 : 0)
                .jugador(jugador)
                .build();
        partidaRepositoryJpa.save(nuevaPartida);
        return pasarEntityADto(nuevaPartida);
    }

    /**
     * Elimina todas las partidas asociadas a un jugador.
     *
     * @param jugador El jugador del cual se desean eliminar las partidas.
     * @throws RuntimeException Si el jugador no tiene partidas que eliminar.
     */
    public void eliminarPartidasDeJugador(JugadorEntityJpa jugador){
        List<PartidaEntityJpa> misPartidas = partidaRepositoryJpa.findByJugador(jugador);
        if (misPartidas.isEmpty()) {
            throw new RuntimeException("El jugador no tiene partidas que eliminar");
        }
        misPartidas.forEach(partida -> partidaRepositoryJpa.delete(partida));
        jugadorRepositoryJpa.save(jugador);
    }

    /**
     * Encuentra y devuelve las partidas de un jugador en forma de DTO.
     *
     * @param jugador El jugador del cual se desean obtener las partidas.
     * @return Una lista de objetos PartidaDtoJpa que representan las partidas del jugador.
     * @throws NotFoundException Si no se encuentran partidas para el jugador.
     */
    public List<PartidaDtoJpa> encuentraPartidasJugador(JugadorEntityJpa jugador){
        List<PartidaEntityJpa> misPartidas = partidaRepositoryJpa.findByJugador(jugador);
        if(misPartidas.isEmpty()){
            throw new NotFoundException("No se le encontraron partidas a este jugador");
        }

        return misPartidas.stream()
                .map(this::pasarEntityADto)
                .collect(Collectors.toList());

    }

    //Metodos privados ---------------------------------------------------------------->>
    /**
     * Convierte una entidad PartidaEntityJpa en un DTO PartidaDtoJpa.
     *
     * @param partidaEntity Entidad PartidaEntityJpa a convertir.
     * @return Objeto PartidaDtoJpa resultante.
     */
    private PartidaDtoJpa pasarEntityADto(PartidaEntityJpa partidaEntity) {
        return PartidaDtoJpa.builder()
                .id(partidaEntity.getId())
                .fecha(partidaEntity.getFecha())
                .mensaje(partidaEntity.getVictorias() == 1 ? "Ganaste :D" : "Perdiste :V")
                .build();
    }

    /**
     * Genera un número aleatorio entre 1 y 12, simulando el resultado de tirar dos dados.
     *
     * @return El número aleatorio generado.
     */
    private int tirarDados(){
        return (int)Math.floor(Math.random() * 12) + 1;
    }


}
