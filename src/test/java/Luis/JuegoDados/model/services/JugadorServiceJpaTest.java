package Luis.JuegoDados.model.services;

import Luis.JuegoDados.exceptions.PlayerListEmptyException;
import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.dto.JugadorDtoJpa;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JugadorServiceJpaTest {

    @InjectMocks
    private JugadorServiceJpa jugadorServiceJpa;
    @Mock
    private JugadorRepositoryJpa jugadorRepositoryJpa;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private PartidaRepositoryJpa partidaRepositoryJpa;

    @Test
    @DisplayName("Dados los datos nombre, email, password, se espera crearse un nuevo usuario y devolver un token")
    public void testRegister() {
        // Configuración de Mockito
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(jwtService.getToken(any())).thenReturn("tokenDePrueba");

        // Llamada al método a probar
        AuthResponse authResponse = jugadorServiceJpa.register("UsuarioTest", "test@example.com", "password123");

        // Verificaciones
        assertNotNull(authResponse);
        assertEquals("tokenDePrueba", authResponse.getToken());

        // Verificar que se llamó al método save en el repositorio
        verify(jugadorRepositoryJpa).save(any(JugadorEntityJpa.class));

        // Verificar que se llamó al método getToken en el servicio Jwt
        verify(jwtService).getToken(any(JugadorEntityJpa.class));
    }

    @Test
    @DisplayName("Encontrar un jugador por su id")
    public void testBuscarJugadorPorId() {
        Long jugadorId = 1L;
        JugadorEntityJpa jugadorDePrueba = new JugadorEntityJpa();
        jugadorDePrueba.setId(jugadorId);

        // Configurar el comportamiento del repositorio simulado
        when(jugadorRepositoryJpa.findAll()).thenReturn(Collections.singletonList(jugadorDePrueba));
        when(jugadorRepositoryJpa.findById(jugadorId)).thenReturn(Optional.of(jugadorDePrueba));

        // Ejecutar el método a probar
        JugadorEntityJpa jugadorEncontrado = jugadorServiceJpa.buscarJugadorPorId(jugadorId);

        // Verificar resultados
        assertNotNull(jugadorEncontrado);
        assertEquals(jugadorId, jugadorEncontrado.getId());
    }

    @Test
    @DisplayName("Actualizar nombre de un jugador a partir de un id y un nuevo nombre")
    public void testActualizarNombreJugador() {
        long jugadorId = 1L;
        JugadorEntityJpa jugadorDePrueba = new JugadorEntityJpa();
        jugadorDePrueba.setId(jugadorId);
        jugadorDePrueba.setNombre("NombreAntiguo");

        // Configurar el comportamiento del repositorio simulado
        when(jugadorRepositoryJpa.save(any(JugadorEntityJpa.class))).thenReturn(jugadorDePrueba);

        // Ejecutar el método a probar
        JugadorDtoJpa jugadorActualizado = jugadorServiceJpa.actualizarNombreJugador(jugadorDePrueba, "NombreNuevo");

        // Verificar resultados
        assertNotNull(jugadorActualizado);
        assertEquals("NombreNuevo", jugadorActualizado.getNombre());
        verify(jugadorRepositoryJpa).save(any(JugadorEntityJpa.class));
    }

    @Test
    @DisplayName("Actualizar porcentaje de éxito de jugador")
    public void testActualizarPorcentajeExitoJugador() {
        // Crear un jugador de prueba
        JugadorEntityJpa jugador = new JugadorEntityJpa(1L, "Jugador1", "correo1@example.com", 50, "contraseña1", Role.USER);

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.save(any(JugadorEntityJpa.class))).thenReturn(jugador);

        // Crear partidas de prueba con diferentes cantidades de victorias
        PartidaEntityJpa partida1 = new PartidaEntityJpa();
        partida1.setVictorias(2);
        PartidaEntityJpa partida2 = new PartidaEntityJpa();
        partida2.setVictorias(1);
        List<PartidaEntityJpa> misPartidas = Arrays.asList(partida1, partida2);

        // Mockear el comportamiento del repositorio de partidas
        when(partidaRepositoryJpa.findByJugador(eq(jugador))).thenReturn(misPartidas);

        // Llamar al método que estás probando
        jugadorServiceJpa.actualizarPorcentajeExitoJugador(jugador);

        // Verificar si el método save fue llamado una vez con el jugador de prueba
        verify(jugadorRepositoryJpa, times(1)).save(eq(jugador));

        // Calcular el porcentaje de éxito esperado
        int porcentajeEsperado = (partida1.getVictorias() + partida2.getVictorias()) * 100 / misPartidas.size();

        // Verificar si el porcentaje de éxito del jugador ha sido actualizado correctamente
        assertEquals(porcentajeEsperado, jugador.getPorcentajeExito());
    }

    @Test
    @DisplayName("Devolver la lista de los jugadores en forma de JugadoresDto")
    public void testObtieneListaJugadores() {
        //datos de prueba
        List<JugadorEntityJpa> jugadores = new ArrayList<>();
        jugadores.add(new JugadorEntityJpa(1L, "correo1@example.com", "Juan", 50, "contraseña1", Role.USER));
        jugadores.add(new JugadorEntityJpa(2L, "correo2@example.com", "Anónimo", 60, "contraseña2", Role.USER));

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.findAll()).thenReturn(jugadores);

        // Llamar al método que se está probando
        List<JugadorDtoJpa> resultado = jugadorServiceJpa.listaJugadores();

        // Verificar el resultado
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Anónimo", resultado.get(1).getNombre());
    }

    @Test
    @DisplayName("Devolver la media de victorias de todos los jugadores")
    public void testCalculaPorcentajeVictoriasGlobales() {
        //datos de prueba
        List<JugadorEntityJpa> jugadores = new ArrayList<>();
        jugadores.add(new JugadorEntityJpa(1L, "Jugador1", "correo1@example.com", 50, "contraseña1", Role.USER));
        jugadores.add(new JugadorEntityJpa(2L, "Jugador2", "correo2@example.com", 60, "contraseña2", Role.USER));

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.findAll()).thenReturn(jugadores);

        // Llamar al método que estás probando
        int porcentajeVictoriasGlobales = jugadorServiceJpa.calculaPorcentajeVictoriasGlobales();

        // Calcular el porcentaje global esperado (en este caso, 55)
        int porcentajeEsperado = (50 + 60) / 2;

        // Verificar si el método findAll fue llamado una vez
        verify(jugadorRepositoryJpa, times(1)).findAll();

        // Verificar el resultado
        assertEquals(porcentajeEsperado, porcentajeVictoriasGlobales);
    }

    @Test
    @DisplayName("Devolver la lista de los jugadores con menor porcentaje de éxito en forma de JugadoresDto")
    public void TestEncuentraPeoresJugadores() {
        //datos de prueba
        List<JugadorEntityJpa> jugadores = new ArrayList<>();
        jugadores.add(new JugadorEntityJpa(1L, "Jugador1", "correo1@example.com", 50, "contraseña1", Role.USER));
        jugadores.add(new JugadorEntityJpa(2L, "Jugador2", "correo2@example.com", 40, "contraseña2", Role.USER));
        jugadores.add(new JugadorEntityJpa(3L, "Jugador3", "correo3@example.com", 40, "contraseña3", Role.USER));
        jugadores.add(new JugadorEntityJpa(4L, "Jugador4", "correo4@example.com", 60, "contraseña4", Role.USER));

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.findAll()).thenReturn(jugadores);

        // Llamar al método que estás probando
        List<JugadorDtoJpa> peoresJugadores = jugadorServiceJpa.peoresJugadores();

        // Verificar si el método findAll fue llamado una vez
        verify(jugadorRepositoryJpa, times(1)).findAll();

        // Verificar el resultado
        assertEquals(2, peoresJugadores.size()); // Debería haber dos jugadores con porcentaje 40
        for (JugadorDtoJpa jugadorDto : peoresJugadores) {
            assertEquals(40, jugadorDto.getPorcentajeExito());// Verificar que ambos tengan porcentaje 40
        }
    }

    @Test
    @DisplayName("Devolver la lista de los peores jugadores cuando la lista esta vacía")
    public void testBuscaJugadoresEnUnaListaVacia() {
        List<JugadorEntityJpa> jugadores = new ArrayList<>();

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.findAll()).thenReturn(jugadores);

        // Configurar el lanzamiento de la excepción cuando la lista esté vacía
        when(jugadorRepositoryJpa.findAll()).thenReturn(Collections.emptyList());

        // Llamar al método que se está probando y verificar que lanza la excepción
        assertThrows(PlayerListEmptyException.class, () -> jugadorServiceJpa.peoresJugadores());
    }

    @Test
    @DisplayName("Devolver la lista de los jugadores con mayor porcentaje de éxito en forma de JugadoresDto")
    public void testEncuentraMejoresJugadores () {
        //datos de prueba
        List<JugadorEntityJpa> jugadores = new ArrayList<>();
        jugadores.add(new JugadorEntityJpa(1L,  "correo1@example.com","Jugador1", 50, "contraseña1", Role.USER));
        jugadores.add(new JugadorEntityJpa(2L,  "correo2@example.com","Jugador2", 40, "contraseña2", Role.USER));
        jugadores.add(new JugadorEntityJpa(3L,  "correo3@example.com","Jugador3", 40, "contraseña3", Role.USER));
        jugadores.add(new JugadorEntityJpa(4L,  "correo4@example.com","Jugador4", 60, "contraseña4", Role.USER));

        // Mockear el comportamiento del repositorio
        when(jugadorRepositoryJpa.findAll()).thenReturn(jugadores);

        // Llamar al método que estás probando
        List<JugadorDtoJpa> mejoresJugadores = jugadorServiceJpa.mejoresJugadores();

        // Verificar si el método findAll fue llamado una vez
        verify(jugadorRepositoryJpa, times(1)).findAll();

        // Verificar el resultado
        assertEquals(1, mejoresJugadores.size()); // Debería haber un jugador con porcentaje 60
        for (JugadorDtoJpa jugadorDto : mejoresJugadores) {
            assertEquals(60, jugadorDto.getPorcentajeExito()); // Verificar que el jugador tenga porcentaje 60
        }
    }

    @Test
    public void testFiltraNombre() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method filtraNombreMethod = JugadorServiceJpa.class.getDeclaredMethod("filtraNombre", String.class);
        filtraNombreMethod.setAccessible(true);

        String resultado1 = (String) filtraNombreMethod.invoke(jugadorServiceJpa, "Jose");
        String resultado2 = (String) filtraNombreMethod.invoke(jugadorServiceJpa, "");

        assertEquals("Jose", resultado1);
        assertEquals("Anónimo", resultado2);
    }

}