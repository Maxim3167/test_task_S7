package tests.controller;

import app.dto.AuthenticationRequestDto;
import app.dto.CreateUserDto;
import app.dto.UserReadDto;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import tests.TestApplicationRunner;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestApplicationRunner.class)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("string")
                .lastName("string")
                .birthDate(LocalDate.of(2001, 12, 25))
                .email("string")
                .password("string")
                .build();
        UserReadDto userReadDto = UserReadDto.builder()
                .firstName("string")
                .lastName("string")
                .birthDate(LocalDate.of(2001,12,25))
                .email("string")
                .build();

        when(userService.saveUser(any(CreateUserDto.class))).thenReturn(userReadDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(createUserDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userReadDto.email()));
    }

    @Test
    void testLogin() throws Exception {
        AuthenticationRequestDto authenticationRequestDto = AuthenticationRequestDto.builder().email("max@gmail.com").password("pass").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(authenticationRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(authenticationRequestDto.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
    }
}
