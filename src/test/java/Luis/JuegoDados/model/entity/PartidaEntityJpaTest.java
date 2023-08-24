package Luis.JuegoDados.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PartidaEntityJpaTest {

    @Test
    public void testBuilderConstructorAndGetters() {

        JugadorEntityJpa jugador = new JugadorEntityJpa();

        PartidaEntityJpa partida = PartidaEntityJpa.builder()
                .id(1L)
                .fecha(LocalDate.now())
                .victorias(1)
                .derrotas(0)
                .jugador(jugador)
                .build();

        assertNotNull(partida);
        assertEquals(1L, partida.getId());
        assertEquals(LocalDate.now(), partida.getFecha());
        assertEquals(1, partida.getVictorias());
        assertEquals(0, partida.getDerrotas());
        assertEquals(jugador, partida.getJugador());
    }

    @Test
    public void testNoArgsConstructorAndSetters(){
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        PartidaEntityJpa partida = new PartidaEntityJpa();
        assertNotNull(partida);

        partida.setId(1L);
        partida.setFecha(LocalDate.now());
        partida.setVictorias(0);
        partida.setDerrotas(1);
        partida.setJugador(jugador);

        assertEquals(1L, partida.getId());
        assertEquals(LocalDate.now(), partida.getFecha());
        assertEquals(0, partida.getVictorias());
        assertEquals(1,partida.getDerrotas());
        assertEquals(jugador, partida.getJugador());

    }




}