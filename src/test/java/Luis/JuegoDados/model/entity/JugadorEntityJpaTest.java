package Luis.JuegoDados.model.entity;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JugadorEntityJpaTest {

    @Test
    public void testAllArgsConstructorAndBuilder(){
        JugadorEntityJpa jugador = JugadorEntityJpa.builder()
                .id(1L)
                .email("mario@ejemplo.com")
                .nombre("Mario")
                .porcentajeExito(35)
                .password("123456")
                .role(Role.USER)
                .build();

        jugador.setId(2L);
        jugador.setEmail("miemail@ejemplo.com");
        jugador.setNombre("Anónimo");
        jugador.setPorcentajeExito(20);
        jugador.setPassword("987654");
        jugador.setRole(Role.ADMIN);

        assertNotNull(jugador);
        assertEquals(2L,jugador.getId());
        assertEquals("miemail@ejemplo.com",jugador.getEmail());
        assertEquals("Anónimo",jugador.getNombre());
        assertEquals(20,jugador.getPorcentajeExito());
        assertEquals("987654",jugador.getPassword());
        assertEquals(Role.ADMIN,jugador.getRole());

    }

    @Test
    public void testGetAuthorities() {
        JugadorEntityJpa jugador = JugadorEntityJpa.builder()
                .role(Role.USER)
                .build();

        Collection<? extends GrantedAuthority> authorities = jugador.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("USER", authorities.iterator().next().getAuthority());
    }

    @Test
    public void testGetUsername() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
                jugador.setEmail("example@example.com");

        String actualEmail = jugador.getUsername();
        assertEquals("example@example.com", actualEmail);
    }

    @Test
    public void testIsAccountNonExpired() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        boolean isAccountNonExpired = jugador.isAccountNonExpired();
        assertTrue(isAccountNonExpired);
    }

    @Test
    public void testIsAccountNonLocked() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        boolean isAccountNonLocked = jugador.isAccountNonLocked();
        assertTrue(isAccountNonLocked);
    }

    @Test
    public void testIsCredentialsNonExpired() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        boolean credentialsNonExpired = jugador.isCredentialsNonExpired();
        assertTrue(credentialsNonExpired);
    }

    @Test
    public void testIsEnabled() {
        JugadorEntityJpa jugador = new JugadorEntityJpa();
        boolean isEnabled = jugador.isEnabled();
        assertTrue(isEnabled);
    }
}