package Luis.JuegoDados.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    public void testTokenGetterAndSetter() {
        AuthResponse authResponse = new AuthResponse();
        String expectedToken = "exampleToken";

        authResponse.setToken(expectedToken);
        String actualToken = authResponse.getToken();

        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testBuilder() {
        String expectedToken = "exampleToken";

        AuthResponse authResponse = AuthResponse.builder()
                .token(expectedToken)
                .build();

        assertEquals(expectedToken, authResponse.getToken());
    }

}