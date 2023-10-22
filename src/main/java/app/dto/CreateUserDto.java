package app.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
@Builder
public record CreateUserDto (
        String firstName,
        String lastName,
        LocalDate birthDate,
        @Email
        String email,
        @NotBlank
        String password
) {
}
