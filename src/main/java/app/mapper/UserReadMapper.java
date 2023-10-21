package app.mapper;

import app.dto.UserReadDto;
import app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User user) {
        return UserReadDto.builder()
                .firstName(user.getPersonalInfo().getFirstName())
                .lastName(user.getPersonalInfo().getLastName())
                .birthDate(user.getPersonalInfo().getBirthDate())
                .email(user.getEmail())
                .build();
    }
}
