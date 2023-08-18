package Luis.JuegoDados.controller;

import Luis.JuegoDados.model.services.JugadorServiceJpa;
import Luis.JuegoDados.model.dto.AuthResponse;
import Luis.JuegoDados.model.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerJpa {

    private final AuthService authService;
    private final JugadorServiceJpa jugadorServiceJpa;

    @Operation(summary = "Registro de un usuario",description = "Registra tus datos para darte de alta como jugador")
    @ApiResponse(responseCode = "200", description = "Registro exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestParam String nombre, @RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(jugadorServiceJpa.register(nombre, email,password));
    }

    @PostMapping(value = "login")
    @Operation(summary = "Inicio de sesión", description = "Introduce tus datos de usuario para generar un token que podrás usar para acceder a los demás endpoints")
    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<AuthResponse> login(@RequestParam String email, String password) {
        return ResponseEntity.ok(authService.login(email, password));
    }

}