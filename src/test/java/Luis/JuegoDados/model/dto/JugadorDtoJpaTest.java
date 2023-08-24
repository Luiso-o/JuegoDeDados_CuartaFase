package Luis.JuegoDados.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorDtoJpaTest {

    @Test
    public void testGetterAndSetter() {
        JugadorDtoJpa jugador = new JugadorDtoJpa();

        jugador.setId(1L);
        jugador.setNombre("Carlos");
        jugador.setPorcentajeExito(20);

        assertNotNull(jugador);
        assertEquals(1L, jugador.getId());
        assertEquals("Carlos", jugador.getNombre());
        assertEquals(20, jugador.getPorcentajeExito());
    }

    @Test
    public void testBuilder() {
       JugadorDtoJpa jugador  = JugadorDtoJpa.builder()
               .id(1L)
               .nombre("Ana")
               .porcentajeExito(78)
               .build();

        assertNotNull(jugador);
        assertEquals(1L, jugador.getId());
        assertEquals("Ana", jugador.getNombre());
        assertEquals(78, jugador.getPorcentajeExito());
    }

}