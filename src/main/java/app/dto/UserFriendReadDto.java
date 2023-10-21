package app.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
public record UserFriendReadDto(
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
