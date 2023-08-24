package Luis.JuegoDados.model.repository;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JugadorRepositoryJpaTest {

    @Autowired
    private JugadorRepositoryJpa jugadorRepositoryJpa;

    @Test
    @DirtiesContext
    public void testFindByEmail() {
        JugadorEntityJpa jugador = new JugadorEntityJpa(1L,"example@example.com","nombreDeEjemplo",30,"mypassword", Role.ADMIN);
        jugadorRepositoryJpa.save(jugador);

        Optional<JugadorEntityJpa> jugadorOptional = jugadorRepositoryJpa.findByEmail("example@example.com");

        assertTrue(jugadorOptional.isPresent(), "El jugador con el correo electrónico 'example@example.com' no se encontró en la base de datos");

        JugadorEntityJpa jugadorRecuperado = jugadorOptional.get();
        assertEquals("example@example.com", jugadorRecuperado.getEmail());
    }
}