package Luis.JuegoDados.security;

import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import Luis.JuegoDados.model.entity.Role;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @InjectMocks
    ApplicationConfig applicationConfig;
    @Mock
    private AuthenticationConfiguration authenticationConfiguration;
    @Mock
    private JugadorRepositoryJpa jugadorRepositoryJpa;

    @Test
    public void testAuthenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManagerMock = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManagerMock);

        ApplicationConfig applicationConfig = new ApplicationConfig(jugadorRepositoryJpa);

        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(authenticationConfiguration);

        assertNotNull(authenticationManager);
        assertEquals(authenticationManagerMock, authenticationManager);
    }


    @Test
    public void testPasswordEncoderBean() {
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testUserDetailServiceBean() {
        JugadorRepositoryJpa jugadorRepositoryJpa = mock(JugadorRepositoryJpa.class);
        when(jugadorRepositoryJpa.findByEmail("alice@example.com"))
                .thenReturn(Optional.of(new JugadorEntityJpa(1L,"alice@example.com","Alice",20,"password123", Role.USER)));

        ApplicationConfig applicationConfig = new ApplicationConfig(jugadorRepositoryJpa);

        UserDetailsService userDetailsService = applicationConfig.userDetailService();

        assertNotNull(userDetailsService);

        UserDetails userDetails = userDetailsService.loadUserByUsername("alice@example.com");
        assertNotNull(userDetails);
        assertEquals("alice@example.com", userDetails.getUsername());

        // Test throwing UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown@example.com"));
    }

}