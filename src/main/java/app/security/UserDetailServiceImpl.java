package app.security;

import app.entity.User;
import app.mapper.JwtUserMapper;
import app.repository.UserRepository;
import app.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUserMapper jwtUserMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        return jwtUserMapper.map(user.get());
//        return userRepository.findUserByEmail(username)
//                .map(user -> new User(user.getEmail(),user.getPassword(), Collections.singleton(user.getRole())))
//                .orElseThrow(() -> {
//                    log.error("Ошибка при авторизации");
//                    return new UsernameNotFoundException("Ошибка при авторизации пользователя: " + username);
//                });
    }
}
