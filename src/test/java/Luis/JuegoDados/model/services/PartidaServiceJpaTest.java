package Luis.JuegoDados.model.services;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
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
        assertEquals("Ganaste :D", partidaDto.getMensaje());

        // Verificar que se llamó al método save en el repositorio
        verify(partidaRepositoryJpa).save(any(PartidaEntityJpa.class));
    }

    @Test
    @DisplayName("Eliminar partidas de jugador sin partidas")
    void eliminarPartidasDeJugador() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        // Configurar el repositorio de partidas para devolver una lista vacía
        when(partidaRepositoryJpa.findByJugador(jugador)).thenReturn(new ArrayList<>());

        // Verificar que se lance una RuntimeException
        assertThrows(RuntimeException.class, () -> partidaServiceJpa.eliminarPartidasDeJugador(jugador));

        // No se debería llamar al método delete en el repositorio
        verify(partidaRepositoryJpa, Mockito.never()).delete(any(PartidaEntityJpa.class));

        // No se debería llamar al método save en el repositorio de jugadores
        verify(jugadorRepositoryJpa, Mockito.never()).save(any(JugadorEntityJpa.class));
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


}