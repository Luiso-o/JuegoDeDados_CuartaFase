package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private JugadorEntityJpa jugador;

    @BeforeEach
    void setUp() {
        jugador = JugadorEntityJpa.builder()
                .id(1)
                .email("alice@example.com")
                .nombre("Alice")
                .porcentajeExito(85)
                .password("secret")
                .role(Role.USER)
                .build();
    }

    @Test
    public void testGetToken() {
        String token = jwtService.getToken(jugador);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGetUsernameFromToken(){
        String token = jwtService.getToken(jugador);
        String usernameExtraidoDeToken = jwtService.getUsernameFromToken(token);
        assertNotNull(usernameExtraidoDeToken);
        assertEquals("alice@example.com",usernameExtraidoDeToken);
    }

    @Test
    public void testTokenIsValid(){
        String token = jwtService.getToken(jugador);
        assertTrue(jwtService.isTokenValid(token,jugador));
    }
}