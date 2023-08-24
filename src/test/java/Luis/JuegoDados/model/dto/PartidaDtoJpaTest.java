package Luis.JuegoDados.model.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PartidaDtoJpaTest {

    @Test
    public void testGetterAndSetter(){
        PartidaDtoJpa partida = new PartidaDtoJpa();
        partida.setId(1L);
        partida.setFecha(LocalDate.now());
        partida.setMensaje("Ganaste :D");

        assertNotNull(partida);
        assertEquals(1L,partida.getId());
        assertEquals(LocalDate.now(),partida.getFecha());
        assertEquals("Ganaste :D",partida.getMensaje());
    }

    @Test
    public void testBuilder(){
        PartidaDtoJpa partida = PartidaDtoJpa.builder()
                .id(1L)
                .fecha(LocalDate.now())
                .mensaje("Ganaste :D")
                .build();

        assertNotNull(partida);
        assertEquals(1L,partida.getId());
        assertEquals(LocalDate.now(),partida.getFecha());
        assertEquals("Ganaste :D",partida.getMensaje());

    }
}