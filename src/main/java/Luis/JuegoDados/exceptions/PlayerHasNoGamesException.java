package Luis.JuegoDados.exceptions;

public class PlayerHasNoGamesException extends RuntimeException{

    public PlayerHasNoGamesException(String name){
        super(name + " no tiene partidas almacenadas");
    }

}
