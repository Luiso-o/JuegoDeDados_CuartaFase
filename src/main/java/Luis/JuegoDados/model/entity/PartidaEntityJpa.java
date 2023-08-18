package Luis.JuegoDados.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información de una partida")
@Table(name = "Partidas")
public class PartidaEntityJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @JoinColumn(name = "Fecha partida")
    private LocalDate fecha;

    @JoinColumn(name = "Victorias")
    private int victorias;

    @JoinColumn(name = "Derrotas")
    private int derrotas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id")
    private JugadorEntityJpa jugador;

}
