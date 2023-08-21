package Luis.JuegoDados.controller;

import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.services.AuthService;
import Luis.JuegoDados.model.services.JugadorServiceJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AuthControllerJpaTest {

    @InjectMocks
    private AuthControllerJpa authControllerJpa;
    @Mock
    private JugadorServiceJpa jugadorServiceJpa;

    @Mock
    private AuthService authService;

    @DisplayName("Dada la información del usuario que queremos crear en register" +
            "esperamos que el usuario este creado y devuelva un token")

    @Test
    void TestRegister(){
        // Simular la respuesta del servicio
        AuthResponse authResponse = AuthResponse.builder()
                .token("tokenDePrueba")
                .build();

        when(jugadorServiceJpa.register("Jose", "jose@ejemplo.com", "1234")).thenReturn(authResponse);

        // Ejecutar el método de prueba
        ResponseEntity<AuthResponse> responseEntity = authControllerJpa.register("Jose", "jose@ejemplo.com", "1234");

        // Verificar que se llamó al método del servicio
        verify(jugadorServiceJpa).register("Jose", "jose@ejemplo.com", "1234");

        // Afirmaciones
        assertNotNull("La respuesta no debe ser nula",responseEntity);
        assertEquals("El código de estado debe ser OK", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("El cuerpo de la respuesta debe ser igual al AuthResponse esperado",authResponse, responseEntity.getBody());
    }

    @DisplayName("Dado las credenciales de inicio de sesión, esperamos un token válido")
    @Test
    void testLogin() {
        // Simular la respuesta del servicio de AuthService
        AuthResponse authResponse = AuthResponse.builder()
                .token("tokenDePrueba")
                .build();
        when(authService.login("jose@ejemplo.com", "1234")).thenReturn(authResponse);

        // Ejecutar el método de prueba
        ResponseEntity<AuthResponse> responseEntity = authControllerJpa.login("jose@ejemplo.com", "1234");

        // Verificar que se llamó al método del servicio
        verify(authService).login("jose@ejemplo.com", "1234");

        // Afirmaciones
        assertNotNull("La respuesta no debe ser nula", responseEntity);
        assertEquals("El código de estado debe ser OK", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("El cuerpo de la respuesta debe ser igual al AuthResponse esperado", authResponse, responseEntity.getBody());
    }

}