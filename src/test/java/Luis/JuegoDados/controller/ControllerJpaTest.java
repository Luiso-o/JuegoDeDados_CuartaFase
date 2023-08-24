package Luis.JuegoDados.controller;

import Luis.JuegoDados.exceptions.PlayerHasNoGamesException;
import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test") //H2
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class ControllerJpaTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthControllerJpa authControllerJpa;
    @Autowired
    private JugadorRepositoryJpa jugadorRepositoryJpa;
    @Autowired
    private ControllerJpa controllerJpa;

    @BeforeEach
    public void setUp(){
        authControllerJpa.register("UsuarioEjemplo1", "usuario1@example.com", "password1");
        authControllerJpa.register("UsuarioEjemplo2", "usuario2@example.com", "password2");
        authControllerJpa.register("UsuarioEjemplo3", "usuario3@example.com", "password3");

    }

    @Test
    @DirtiesContext
    public void testActualizarJugador() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario1@example.com", "password1");

        // Obtener el token de la respuesta de inicio de sesión
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        mockMvc.perform(MockMvcRequestBuilders.put("/jugador/{id}", 1L)
                        .param("nombre", "NuevoNombre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("NuevoNombre"));

    }

    @Test
    @DirtiesContext
    public void testTirarDados() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario2@example.com", "password2");
        // Obtener el token de la respuesta de inicio de sesión
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        mockMvc.perform(MockMvcRequestBuilders.post("/jugador/{id}/juego", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value(
                        Matchers.either(Matchers.is("Ganaste :D"))
                                .or(Matchers.is("Perdiste :V"))
                ));


    }

    @Test
    @DirtiesContext
    public void alEliminarPartidas_deberiaRetornarNoContent() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario2@example.com", "password2");
        // Obtener el token de la respuesta de inicio de sesión
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/jugador/{id}/partidas", 2L)
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string("")); // No hay contenido en la respuesta

            // Intentar eliminar partidas nuevamente, debería lanzar una excepción
            mockMvc.perform(MockMvcRequestBuilders.delete("/jugador/{id}/partidas", 2L)
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.mensaje").value("UsuarioEjemplo no tiene partidas que eliminar"))
                    .andExpect(jsonPath("$.detalle").value("PlayerHasNoGamesException"));
        } catch (Exception e) {
            if (e.getCause() instanceof PlayerHasNoGamesException) {
                // La excepción fue lanzada, lo cual es lo esperado
                return;
            }
            throw e; // Lanzar cualquier otra excepción no esperada
        }
    }

    @Test
    @DirtiesContext
    public void alObtenerListaDeJugadores_deberiaRetornarListaProcesada() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario3@example.com", "password3");
        // Obtener el token de la respuesta de inicio de sesión
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        mockMvc.perform(MockMvcRequestBuilders.get("/jugador")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray()); // La respuesta es un array (lista)
    }

    @Test
    @DirtiesContext
    public void testMuestraPartidasDeUnJugadorSinPartidas() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario3@example.com", "password3");
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        // Realiza la solicitud GET al endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/jugador/{id}/partidas", 3L)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound()) // Esperamos un estado NOT FOUND (404) debido a la excepción lanzada
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Esperamos JSON en la respuesta
                .andExpect(jsonPath("$.error").value("UsuarioEjemplo no tiene partidas almacenadas"));

    }

    @Test
    @DirtiesContext
    public void testMuestraPorcentajeVictorias() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario1@example.com", "password1");
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/jugador/ranking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + authToken));

        int expectedPercentage = 0; // Porcentaje esperado ya que no existen partidas

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.['Porcentaje de victorias globales ']").value(expectedPercentage + "%"));
    }

    @Test
    @DirtiesContext
    public void testPeoresPorcentajes() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario2@example.com", "password2");
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/jugador/ranking/peores")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + authToken));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DirtiesContext
    public void testMejoresPorcentajes() throws Exception {
        ResponseEntity<AuthResponse> loginResponse = authControllerJpa.login("usuario3@example.com", "password3");
        String authToken = Objects.requireNonNull(loginResponse.getBody()).getToken();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/jugador/ranking/mejores")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + authToken));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


}

