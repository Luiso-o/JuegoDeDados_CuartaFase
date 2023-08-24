package Luis.JuegoDados.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    SecurityConfig securityConfig;
    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private AuthenticationProvider authenticationProvider;
    @Mock
    private HttpSecurity httpSecurity;
    @Mock
    private HttpSecurity httpSecurityConfigured;

}