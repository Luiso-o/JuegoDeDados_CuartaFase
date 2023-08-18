package Luis.JuegoDados.controller;

import Luis.JuegoDados.model.dto.JugadorDtoJpa;
import Luis.JuegoDados.model.dto.PartidaDtoJpa;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.services.JugadorServiceJpa;
import Luis.JuegoDados.model.services.PartidaServiceJpa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Builder
@RequestMapping("Jugador")
@SecurityRequirement(name = "bearerauth")
public class ControllerJpa {

    @Autowired
    private final JugadorServiceJpa jugadorServiceJpa;

    @Autowired
    private final PartidaServiceJpa partidaServiceJpa;

    @Operation(summary = "Actualiza el nombre de un Jugador", description = "Actualizará el nombre del jugador correspondiente al id introducido")
    @ApiResponse(responseCode = "200", description = "Nombre de jugador actualizado con éxito")
    @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @PutMapping("/{id}")
    public ResponseEntity<JugadorDtoJpa> actualizarJugador
            (@PathVariable Long id,
             @Nullable
             @RequestParam
             @Pattern(regexp = "^[a-zA-Z]*$",message = "El nombre debe contener solo letras") String nombre)
    {
        JugadorEntityJpa jugadorEntidad = jugadorServiceJpa.buscarJugadorPorId(id);
        JugadorDtoJpa jugador = jugadorServiceJpa.actualizarNombreJugador(jugadorEntidad, nombre);
        return ResponseEntity.ok(jugador);
    }

  @Operation(summary = "Juega una partida", description = "Lanza los dados y devuelve los resultados de la partida")
    @ApiResponse(responseCode = "200", description = "Partida realizada y guardada con éxito")
  @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @PostMapping("/{id}/juego")
    public ResponseEntity<PartidaDtoJpa> tirarDados(@PathVariable long id){
        JugadorEntityJpa jugador = jugadorServiceJpa.buscarJugadorPorId(id);
       PartidaDtoJpa nuevaPartida = partidaServiceJpa.crearPartida(jugador);
        jugadorServiceJpa.actualizarPorcentajeExitoJugador(jugador);
       return ResponseEntity.ok(nuevaPartida);
    }

      @Operation(summary = "Elimina las partidas de un Jugador", description = "recibe el id de un jugador y elimina sus partidas")
    @ApiResponse(responseCode = "204", description = "Partidas eliminadas con éxito")
      @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @DeleteMapping("/{id}/partidas")
    public ResponseEntity<Void> eliminarPartidasDeUnJugador(@PathVariable long id) {
        JugadorEntityJpa jugador = jugadorServiceJpa.buscarJugadorPorId(id);
        partidaServiceJpa.eliminarPartidasDeJugador(jugador);
        jugadorServiceJpa.actualizarPorcentajeExitoJugador(jugador);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ver lista de jugadores",description = "Devuelve la lista de los jugadores con su porcentaje éxito")
    @ApiResponse(responseCode = "200", description = "Lista procesada con éxito")
    @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @GetMapping()
    public List<JugadorDtoJpa> obtenerListaJugadoresConPorcentajeMedioExito() {
        List<JugadorDtoJpa> jugadores = jugadorServiceJpa.listaJugadores();
        return ResponseEntity.ok(jugadores).getBody();
    }

    @Operation(summary = "Busca Partidas de un jugador", description = "Encontrará las partidas de un jugador por su id")
    @ApiResponse(responseCode = "200", description = "Partidas encontradas con éxito")
    @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<PartidaDtoJpa>> muestraPartidasJugador(@PathVariable long id){
        JugadorEntityJpa jugador = jugadorServiceJpa.buscarJugadorPorId(id);
        List<PartidaDtoJpa> partidas = partidaServiceJpa.encuentraPartidasJugador(jugador);
        return ResponseEntity.ok(partidas);
    }

   @Operation(summary = "Ranking de victorias",description = "Muestra el porcentaje total de victorias de todos los jugadores")
    @ApiResponse(responseCode = "200", description = "Porcentaje realizado con éxito")
   @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @GetMapping("/ranking")
    public ResponseEntity<Map<String, Object>> muestraPorcentajeVictorias(){
        int porcentaje = jugadorServiceJpa.calculaPorcentajeVictoriasGlobales();
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Porcentaje de victorias globales ", porcentaje + "%");
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Revisa peores jugadores",description = "Muestra los jugadores con el porcentaje más bajo de victorias")
    @ApiResponse(responseCode = "200", description = "Lista encontrada con éxito")
    @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @GetMapping("/ranking/peores")
    public ResponseEntity<List<JugadorDtoJpa>> peoresPorcentajes() {
           List<JugadorDtoJpa> peoresJugadores = jugadorServiceJpa.peoresJugadores();
           return ResponseEntity.ok(peoresJugadores);
    }

    @Operation(summary = "Revisa mejores jugadores",description = "Muestra los jugadores con el porcentaje más alto de victorias")
    @ApiResponse(responseCode = "200", description = "Lista encontrada con éxito")
    @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    @GetMapping("/ranking/mejores")
    public ResponseEntity<List<JugadorDtoJpa>> mejoresPorcentajes() {
        List<JugadorDtoJpa> peoresJugadores = jugadorServiceJpa.mejoresJugadores();
        return ResponseEntity.ok(peoresJugadores);
    }

}
