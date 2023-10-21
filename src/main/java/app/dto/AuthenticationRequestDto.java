package app.dto;


public record AuthenticationRequestDto(
        String email,
        String password

) {
}
