package app.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Builder
public record CreateUserDto (
        String firstName,
        String lastName,
        LocalDate birthDate,
        String email,
        String password
) {
}
