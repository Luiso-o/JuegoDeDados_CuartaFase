package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    JugadorRepositoryJpa jugadorRepositoryJpa;

    @DisplayName("Dado un correo y contraseña válidos, esperamos que se genere un token de autenticación")

    @Test
    void login() {
        // Datos de prueba
        String email = "jose@example.com";
        String password = "contrasena";

        // Simular autenticación exitosa
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        // Simular búsqueda de usuario por correo
        JugadorEntityJpa jugadorEntity = new JugadorEntityJpa();
        jugadorEntity.setId(1L);
        when(jugadorRepositoryJpa.findByEmail(email)).thenReturn(Optional.of(jugadorEntity));

        // Simular generación de token
        String token = "tokenDePrueba";
        when(jwtService.getToken(jugadorEntity)).thenReturn(token);

        // Ejecutar el método de prueba
        AuthResponse authResponse = authService.login(email, password);

        // Afirmaciones
        assertNotNull(authResponse,"La respuesta no debe ser nula");
        assertEquals( token, authResponse.getToken(),"El token debe coincidir");
    }
}
