package app.dto;

import lombok.Getter;

public record AuthenticationRequestDto(
        String email,
        String password

) {
}
