package app.mapper;

import app.entity.User;
import app.security.jwt.JwtUser;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtUserMapper implements Mapper<User, JwtUser> {
    @Override
    public JwtUser map(User user) {
        return new JwtUser(
                user.getId(),
                user.getPersonalInfo().getFirstName(),
                user.getPersonalInfo().getLastName(),
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(user.getRole())
        );
    }
}
