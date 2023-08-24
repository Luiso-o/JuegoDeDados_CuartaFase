package Luis.JuegoDados.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNotFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        String expectedMessage = "Lista de jugadores vacía";

        try {
            throw new PlayerListEmptyException();
        } catch (PlayerListEmptyException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

}