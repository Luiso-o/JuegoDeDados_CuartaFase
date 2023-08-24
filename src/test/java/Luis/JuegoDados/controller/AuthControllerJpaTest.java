package Luis.JuegoDados.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerJpaTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testRegisterAndLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .param("nombre", "UsuarioEjemplo")
                        .param("email", "usuario@example.com")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Realizar el proceso de inicio de sesión
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .param("email", "usuario@example.com")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}