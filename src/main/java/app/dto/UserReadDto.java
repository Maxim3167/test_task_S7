package app.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
@Builder
public record UserReadDto(
        String lastName,
        String firstName,
        LocalDate birthDate,
        String email
) {
}
