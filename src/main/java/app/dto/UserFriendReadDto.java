package app.dto;

import lombok.Builder;
@Builder
public record UserFriendReadDto(
        String firstName,
        String lastName
) {
}
