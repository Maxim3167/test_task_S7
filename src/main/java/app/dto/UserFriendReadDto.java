package app.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserFriendReadDto(
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
