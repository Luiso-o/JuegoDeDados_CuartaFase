package Luis.JuegoDados.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @Test
    public void testAdminRole() {
        Role role = Role.ADMIN;
        assertEquals("ADMIN", role.toString());
    }

    @Test
    public void testUserRole() {
        Role role = Role.USER;
        assertEquals("USER", role.toString());
    }

}