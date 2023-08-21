package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import java.security.Key;
import java.util.Base64;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;
    @Mock
    private Claims claims;
    @Mock
    private Jws<Claims> jws;
    @Spy
    private Function<Claims, String> claimsResolver = claims -> "ResultadoSimulado";

    @Test
    @DisplayName("Generar el token de un jugador")
    void testGetToken() {
        //Datos de prueba
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        jugador.setNombre("Juan");
        jugador.setEmail("test@example.com");
        jugador.setRole(Role.USER);

        //Act
        String token = jwtService.getToken(jugador);

        //Assert
        assertNotNull(token);
    }

    @Test
    @DisplayName("Prueba del método getKey")
    public void testGetKey() {
        byte[] decodedKeyBytes = Base64.getDecoder().decode("MzI3MjQ1NDA2NTI2MTMzOTQzODc4MzQxMTk5MTYxMzE");
        Key expectedKey = Keys.hmacShaKeyFor(decodedKeyBytes);

        Key actualKey = jwtService.getKey();

        assertEquals("La clave generada no coincide con la clave esperada",expectedKey, actualKey);
    }

    @Test
    @DisplayName("Prueba del método getUsernameFromToken")
    void testGetUsernameFromToken() {

    }

    @Test
    @DisplayName("Verificar validez de token para jugador inválido")
    void testIsTokenValid() {
        JwtService jwtService = new JwtService();

        // Crear una entidad JugadorEntityJpa como usuario
        JugadorEntityJpa jugador = JugadorEntityJpa.builder()
                .email("carlos@ejemplo.com")
                .password("password")
                .role(Role.USER)
                .build();

        // Generar un token para el jugador
        String token = jwtService.getToken(jugador);

        // Llamada al método que se está probando
        boolean isValid = jwtService.isTokenValid(token, jugador);

        // Verificación
        assertTrue(isValid, "El token debería ser válido para el jugador proporcionado.");
    }

    @Test
    @DisplayName("Prueba del método getClaim")
    void testGetClaim() {

    }
}