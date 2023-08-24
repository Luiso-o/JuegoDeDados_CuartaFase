package Luis.JuegoDados.model.repository;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.PartidaEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartidaRepositoryJpaTest {

    @Autowired
    PartidaRepositoryJpa partidaRepositoryJpa;
    @Autowired
    JugadorRepositoryJpa jugadorRepositoryJpa;

    @Test
    public void testFindByJugador(){
        JugadorEntityJpa jugador = new JugadorEntityJpa(1L,"miemail@ejemplo.com","Karal",23,"myPassword", Role.USER);
        jugadorRepositoryJpa.save(jugador);
        PartidaEntityJpa partida = new PartidaEntityJpa(1L, LocalDate.now(),1,0,jugador);
        partidaRepositoryJpa.save(partida);

        List<PartidaEntityJpa> partidaEncontrada = partidaRepositoryJpa.findByJugador(jugador);

        assertTrue(partidaEncontrada.stream().anyMatch(p -> p.getId().equals(partida.getId())));
    }


}