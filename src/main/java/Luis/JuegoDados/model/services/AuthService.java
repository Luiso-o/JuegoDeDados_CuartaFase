package Luis.JuegoDados.model.services;

import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.repository.JugadorRepositoryJpa;
import Luis.JuegoDados.model.entity.JugadorEntityJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JugadorRepositoryJpa jugadorRepositoryJpa;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Realiza el proceso de autenticación de un usuario y genera un token de acceso si las credenciales son válidas.
     *
     * @param email Objeto que contiene el nombre de usuario y la contraseña ingresados por el usuario.
     * @return Un objeto {@link AuthResponse} que contiene el token de acceso generado.
     * @throws AuthenticationException Si las credenciales no son válidas o la autenticación falla.
     */
    public AuthResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        JugadorEntityJpa user = jugadorRepositoryJpa.findByEmail(email).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
