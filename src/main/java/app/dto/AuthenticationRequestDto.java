package app.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
public record AuthenticationRequestDto(
        @Email
        String email,
        @NotBlank
        String password

) {
}
