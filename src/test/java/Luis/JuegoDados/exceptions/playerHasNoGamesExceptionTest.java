package Luis.JuegoDados.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerHasNoGamesExceptionTest {
    @Test
    public void testExceptionMessage() {
        String playerName = "Catarina";
        String expectedMessage = playerName + " no tiene partidas que eliminar";

        try {
            throw new PlayerHasNoGamesException(playerName);
        } catch (PlayerHasNoGamesException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

}