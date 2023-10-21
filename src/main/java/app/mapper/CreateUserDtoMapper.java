package app.mapper;

import app.dto.CreateUserDto;
import app.entity.PersonalInfo;
import app.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static app.entity.Role.USER;

@Component
@RequiredArgsConstructor
public class CreateUserDtoMapper implements Mapper<CreateUserDto, User> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public User map(CreateUserDto createUserDto) {
        String password = Optional.ofNullable(createUserDto.password())
                .filter(pass -> StringUtils.hasText(createUserDto.password()))
                .map(passwordEncoder::encode)
                .get();
        return User.builder()
                .personalInfo(PersonalInfo.builder()
                        .lastName(createUserDto.lastName())
                        .firstName(createUserDto.firstName())
                        .birthDate(createUserDto.birthDate())
                        .build())
                .role(USER)
                .email(createUserDto.email())
                .password(password)
                .build();
    }
}
