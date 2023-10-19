package app.service;

import app.dto.CreateUserDto;
import app.dto.UserReadDto;
import app.mapper.CreateUserDtoMapper;
import app.mapper.UserReadMapper;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CreateUserDtoMapper createUserDtoMapper;
    private final UserReadMapper userReadMapper;

    @Transactional
    public UserReadDto saveUser(CreateUserDto createUserDto) {
        // валидацию сделать на уровне контроллеров
       return Optional.of(createUserDto)
                .map(createUserDtoMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow(() -> {
                    log.error("Ошибка при сохранении пользователя");
                    return new RuntimeException("Ошибка при сохранении пользователя");
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .map(user -> new User(user.getEmail(),user.getPassword(), Collections.singleton(user.getRole())))
                .orElseThrow(() -> {
                    log.error("Ошибка при авторизации");
                    return new UsernameNotFoundException("Ошибка при авторизации пользователя: " + username);
                });
    }
}
