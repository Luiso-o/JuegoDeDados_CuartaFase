package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.dto.JugadorDtoJpa;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.PartidaEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import Luis.JuegoDados.model.repository.PartidaRepositoryJpa;
import Luis.JuegoDados.model.dto.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Service
public class JugadorServiceJpa {

    private JugadorRepositoryJpa jugadorRepositoryJpa;
    private PartidaRepositoryJpa partidaRepositoryJpa;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(String nombre, String email, String password){
        JugadorEntityJpa usuario = JugadorEntityJpa.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nombre(filtraNombre(nombre))
                .porcentajeExito(0)
                .role(Role.USER)
                .build();

        jugadorRepositoryJpa.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

    /**
     * Busca y devuelve un jugador por su ID.
     *
     * @param id El ID del jugador que se desea buscar.
     * @return El objeto JugadorEntityJpa que representa al jugador con el ID especificado.
     * @throws NotFoundException Si la lista de jugadores está vacía.
     * @throws RuntimeException Si no se encuentra un jugador con el ID proporcionado.
     */
    public JugadorEntityJpa buscarJugadorPorId(Long id){

        try {
            List<JugadorEntityJpa> misJugadores = jugadorRepositoryJpa.findAll();

            if (misJugadores.isEmpty()) {
                throw new NotFoundException("Lista de jugadores vacía");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("Lista de jugadores vacía");
        }

     return jugadorRepositoryJpa.findById(id)
             .orElseThrow(() -> new RuntimeException("No se encontró el jugador con el ID proporcionado."));
    }

    /**
     * Actualiza el nombre de un jugador en la base de datos y devuelve la información actualizada en forma de DTO.
     *
     * @param jugador El objeto de entidad de jugador que se desea actualizar.
     * @param nombre El nuevo nombre que se desea asignar al jugador.
     * @return Un objeto DTO que representa al jugador actualizado.
     */
    public JugadorDtoJpa actualizarNombreJugador(JugadorEntityJpa jugador, String nombre){
        String nombreFinal = filtraNombre(nombre);
        jugador.setNombre(nombreFinal);
        jugadorRepositoryJpa.save(jugador);
        return pasarEntidadADto(jugador);
    }

    /**
     * Actualiza el porcentaje de éxito de un jugador y guarda los datos actualizados en la base de datos.
     *
     * @param jugador El objeto JugadorEntityJpa que representa al jugador cuyo porcentaje de éxito se actualizará.
     */
   @Transactional
    public void actualizarPorcentajeExitoJugador(JugadorEntityJpa jugador){
        int porcentajeExitoActualizado = calculaPorcentajeExitoDeUnJugador(jugador);
        jugador.setPorcentajeExito(porcentajeExitoActualizado);
        jugadorRepositoryJpa.save(jugador);
    }

    /**
     * Retorna una lista de todos los jugadores en forma de DTO.
     *
     * @return Una lista de objetos JugadorDtoJpa que representan a todos los jugadores.
     * @throws NotFoundException Si la lista de jugadores está vacía.
     */
    public List<JugadorDtoJpa> listaJugadores() {
        List<JugadorEntityJpa> jugadores = jugadorRepositoryJpa.findAll();
        if (jugadores.isEmpty()) {
            throw new NotFoundException("Lista de Jugadores vacía");
        }
        return jugadores.stream().map(this::pasarEntidadADto)
                .collect(Collectors.toList());
    }

    /**
     * Calcula y devuelve el porcentaje global de victorias entre todos los jugadores.
     *
     * @return El porcentaje global de victorias.
     */
    public int calculaPorcentajeVictoriasGlobales(){
        List<JugadorEntityJpa> jugadores = jugadorRepositoryJpa.findAll();
        if(jugadores.isEmpty()){return 0;}
        int porcentajeExitoGlobal = jugadores.stream()
                .mapToInt(JugadorEntityJpa::getPorcentajeExito)
                .sum();
        if(porcentajeExitoGlobal == 0){return 0;}
        return porcentajeExitoGlobal/jugadores.size();
    }

    /**
     * Obtiene una lista de los peores jugadores basados en su porcentaje de éxito.
     *
     * @return Una lista de objetos JugadorDtoJpa que representan a los peores jugadores.
     * @throws NotFoundException Si no se encuentran jugadores en la base de datos.
     */
    public List<JugadorDtoJpa> peoresJugadores(){
        List<JugadorEntityJpa> todosLosJugadores = jugadorRepositoryJpa.findAll();
        List<JugadorDtoJpa> peoresJugadores = new ArrayList<>();
        int porcentajeMasBajo = 100; //Partimos con el porcentaje más alto

        if (todosLosJugadores.isEmpty()) {
          throw  new NotFoundException("No hay jugadores en la base de datos");
        }

        for (JugadorEntityJpa jugador : todosLosJugadores) {
            int miPorcentajeDeExito = jugador.getPorcentajeExito();

            if(miPorcentajeDeExito < porcentajeMasBajo){
                // Si encontramos un jugador con un porcentaje más bajo, limpiamos la lista anterior
                peoresJugadores.clear();
                porcentajeMasBajo = miPorcentajeDeExito;
            }
            if (miPorcentajeDeExito == porcentajeMasBajo) {
                JugadorDtoJpa jugadorDto = pasarEntidadADto(jugador);
                peoresJugadores.add(jugadorDto);
            }
        }
        return peoresJugadores;
    }

    /**
     * Obtiene una lista de los jugadores con el mismo porcentaje más alto de éxito.
     *
     * @return Una lista de objetos JugadorDtoJpa que representan a los jugadores con el mismo porcentaje más alto.
     * @throws NotFoundException Si no se encuentran jugadores en la base de datos.
     */
    public List<JugadorDtoJpa> mejoresJugadores() throws NotFoundException {
        List<JugadorEntityJpa> todosLosJugadores = jugadorRepositoryJpa.findAll();
        List<JugadorDtoJpa> mejoresJugadores = new ArrayList<>();
        int porcentajeMasAlto = 0; // Partimos con el porcentaje más bajo

        if (todosLosJugadores.isEmpty()) {
            throw new NotFoundException("No hay jugadores en la base de datos");
        }

        for (JugadorEntityJpa jugador : todosLosJugadores) {
            int miPorcentajeDeExito = jugador.getPorcentajeExito();

            if (miPorcentajeDeExito > porcentajeMasAlto) {
                // Si encontramos un jugador con un porcentaje más alto, limpiamos la lista anterior
                mejoresJugadores.clear();
                porcentajeMasAlto = miPorcentajeDeExito;
            }
            if (miPorcentajeDeExito == porcentajeMasAlto) {
                JugadorDtoJpa jugadorDto = pasarEntidadADto(jugador);
                mejoresJugadores.add(jugadorDto);
            }
        }

        return mejoresJugadores;
    }

    //Metodos Privados----------------------------------------------------------------------->>

    /**
     * Calcula el porcentaje de éxito de un jugador basado en la cantidad de victorias en sus partidas.
     * <p>
     * Este método calcula el porcentaje de éxito de un jugador identificado por su ID. El porcentaje se calcula
     * dividiendo la suma de las victorias en las partidas del jugador por la cantidad total de partidas jugadas.
     * El resultado se multiplica por 100 para obtener el porcentaje.
     *
     * @param jugador La entidad del jugador para el que se va a calcular el porcentaje de éxito.
     * @return El porcentaje de éxito del jugador en sus partidas. Si no hay partidas registradas, devuelve 0.
     * @throws RuntimeException Si no se encuentra el jugador con el ID proporcionado.
     */
    private int calculaPorcentajeExitoDeUnJugador(JugadorEntityJpa jugador){
        int porcentajeExito = 0, victorias;
        List<PartidaEntityJpa> misPartidas = partidaRepositoryJpa.findByJugador(jugador);
        int cantidadPartidas = misPartidas.size();

        victorias = misPartidas.stream().mapToInt(PartidaEntityJpa::getVictorias).sum();

        if (cantidadPartidas > 0) {
            porcentajeExito = (victorias * 100) / cantidadPartidas;
        }
        return porcentajeExito;
    }

    /**

     * Convierte una entidad JugadorEntityJpa en un DTO JugadorDtoJpa.
     * <p>
     * Esta función realiza la conversión de una entidad JugadorEntityJpa a un DTO JugadorDtoJpa,
     * asignando las propiedades relevantes de la entidad al DTO resultante.
     *
     * @param jugador La entidad JugadorEntityJpa que se va a convertir.
     * @return Un objeto JugadorDtoJpa que representa la entidad convertida.
     */
    private JugadorDtoJpa pasarEntidadADto(JugadorEntityJpa jugador) {
        return JugadorDtoJpa.builder()
                .id(jugador.getId())
                .nombre(jugador.getNombre())

                .porcentajeExito(jugador.getPorcentajeExito())
                .build();
    }

    /**
     * Filtra y normaliza un nombre, retornando "Anónimo" si el nombre es nulo, vacío o contiene solo espacios en blanco.
     *
     * @param cadena La cadena de texto a filtrar y normalizar.
     * @return El nombre filtrado o "Anónimo" si la cadena es nula, vacía o contiene solo espacios en blanco.
     */
    private String filtraNombre(String cadena){
         if (cadena != null && !cadena.isBlank()) {
            // Eliminar espacios en blanco y números de la cadena
            String nombreFiltrado = cadena.replaceAll("\\s+", "").replaceAll("\\d+", "");

            return nombreFiltrado.isEmpty() ? "Anónimo" : nombreFiltrado;
        } else {
            return "Anónimo";
        }
    }
}
