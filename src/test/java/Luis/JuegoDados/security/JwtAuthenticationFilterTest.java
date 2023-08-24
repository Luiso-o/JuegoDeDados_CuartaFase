package Luis.JuegoDados.security;

import Luis.JuegoDados.model.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;


}

