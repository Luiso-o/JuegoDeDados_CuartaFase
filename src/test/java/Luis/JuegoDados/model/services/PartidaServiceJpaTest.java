package Luis.JuegoDados.model.services;

import Luis.JuegoDados.exceptions.PlayerHasNoGamesException;
import Luis.JuegoDados.model.dto.PartidaDtoJpa;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.PartidaEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import Luis.JuegoDados.model.repository.PartidaRepositoryJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartidaServiceJpaTest {

    @InjectMocks
    private PartidaServiceJpa partidaServiceJpa;
    @Mock
    private PartidaRepositoryJpa partidaRepositoryJpa;
    @Mock
    private JugadorRepositoryJpa jugadorRepositoryJpa;

    @Test
    @DisplayName("Crear partida exitosamente")
    void testCrearPartida() {
        JugadorEntityJpa jugador = new JugadorEntityJpa(1L, "Jugador1", "correo1@example.com", 50, "contraseña1", Role.USER);

        // Simular el comportamiento del repositorio
        when(partidaRepositoryJpa.save(any(PartidaEntityJpa.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Llamada al método a probar
        PartidaDtoJpa partidaDto = partidaServiceJpa.crearPartida(jugador);

        // Verificaciones
        assertNotNull(partidaDto);
        assertEquals(LocalDate.now(), partidaDto.getFecha());
        assertTrue(partidaDto.getMensaje().equals("Ganaste :D") || partidaDto.getMensaje().equals("Perdiste :V"));

        // Verificar que se llamó al método save en el repositorio
        verify(partidaRepositoryJpa).save(any(PartidaEntityJpa.class));
    }

    @Test
    @DisplayName("Eliminar partidas de jugador sin partidas")
    void testEliminarPartidasDeUnJugador_ListaVacia() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        // Simular el comportamiento del repositorio para devolver una lista vacía de partidas
        when(partidaRepositoryJpa.findByJugador(jugador)).thenReturn(Collections.emptyList());

        // Verificar que se lance la excepción playerHasNoGamesException
        assertThrows(PlayerHasNoGamesException.class, () -> partidaServiceJpa.eliminarPartidasDeJugador(jugador));

        // Verificar que no se llamó al método delete en el repositorio de partidas
        verify(partidaRepositoryJpa, never()).delete(any(PartidaEntityJpa.class));

        // Verificar que no se llamó al método save en el repositorio de jugadores
        verify(jugadorRepositoryJpa, never()).save(jugador);
    }

    @Test
    @DisplayName("Eliminar partidas de un jugador")
    void testEliminarPartidasDeUnJugador() {
        // Datos de prueba
        JugadorEntityJpa jugador = new JugadorEntityJpa(1L, "Jugador1", "correo1@example.com", 50, "contraseña1", Role.USER);
        PartidaEntityJpa partida1 = new PartidaEntityJpa(1L, LocalDate.now(), 1, 0, jugador);
        PartidaEntityJpa partida2 = new PartidaEntityJpa(2L, LocalDate.now(), 0, 1, jugador);

        // Simular el comportamiento del repositorio
        when(partidaRepositoryJpa.findByJugador(jugador)).thenReturn(Arrays.asList(partida1, partida2));

        // Llamada al método a probar
        partidaServiceJpa.eliminarPartidasDeJugador(jugador);

        // Verificaciones
        verify(partidaRepositoryJpa, times(2)).delete(any(PartidaEntityJpa.class));
        verify(jugadorRepositoryJpa).save(jugador);
    }


    @Test
    @DisplayName("Buscar partidas de jugador")
    void testEncuentraPartidasJugador() {
        // Crear un jugador ficticio y sus partidas asociadas
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        PartidaEntityJpa partida1 = new PartidaEntityJpa();
        PartidaEntityJpa partida2 = new PartidaEntityJpa();
        List<PartidaEntityJpa> partidas = new ArrayList<>();
        partidas.add(partida1);
        partidas.add(partida2);

        // Configurar el comportamiento esperado del repositorio
        when(partidaRepositoryJpa.findByJugador(jugador)).thenReturn(partidas);

        // Llamada al método a probar
        List<PartidaDtoJpa> partidaDtos = partidaServiceJpa.encuentraPartidasJugador(jugador);

        // Verificaciones
        assertEquals(partidas.size(), partidaDtos.size()); // Verificar que se devuelvan todas las partidas
    }

    @Test
    @DisplayName("Buscar partidas de jugador sin partidas asociadas")
    void testEncuentraPartidasJugadorSinPartidas_ListaVacia() {
        // Crear un jugador ficticio sin partidas asociadas
        JugadorEntityJpa jugador = new JugadorEntityJpa();

        // Configurar el comportamiento esperado del repositorio para devolver una lista vacía de partidas
        when(partidaRepositoryJpa.findByJugador(jugador)).thenReturn(Collections.emptyList());

        // Verificar que se lance la excepción adecuada cuando el jugador no tiene partidas
        assertThrows(PlayerHasNoGamesException.class, () -> partidaServiceJpa.encuentraPartidasJugador(jugador));
    }


}