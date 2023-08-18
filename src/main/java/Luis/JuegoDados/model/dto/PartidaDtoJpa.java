package Luis.JuegoDados.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaDtoJpa {

    @Schema(description = "Identificador único de la partida",example = "1")
    private Long id;

    @Schema(description = "Fecha de la partida en formato dd-MM-yyyy", example = "2023-08-11")
    private LocalDate fecha;

    @Schema(description = "Mensaje que indica si el jugador ganó o perdió", example = "Ganaste :D")
    private String mensaje;
}
