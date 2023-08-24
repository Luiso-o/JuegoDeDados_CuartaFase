package Luis.JuegoDados.exceptions;

import org.webjars.NotFoundException;

public class PlayerListEmptyException extends NotFoundException {
    public PlayerListEmptyException(){
        super("Lista de jugadores vacía");
    }
}
