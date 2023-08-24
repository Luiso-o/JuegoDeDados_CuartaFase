package Luis.JuegoDados.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(Long id){
        super("No se encontró el jugador con el id " + id);
    }
}
