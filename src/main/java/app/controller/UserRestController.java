package app.controller;

import app.dto.AuthenticationRequestDto;
import app.dto.CreateUserDto;
import app.dto.UserFriendReadDto;
import app.dto.UserReadDto;
import app.entity.User;
import app.repository.UserRepository;
import app.security.jwt.JwtTokenProvider;
import app.service.UserFriendService;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserFriendService userFriendService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registerUser(@RequestBody CreateUserDto createUserDto){
       return userService.saveUser(createUserDto);
    }


    @GetMapping("/friends")
    public List<UserFriendReadDto> findAllFriends(@RequestParam Long id, @RequestHeader(required = false) String token){
        return userFriendService.findAllFriendsById(id);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto dto){
        try {
            String username = dto.email();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.password()));
            Optional<User> user = userRepository.findUserByEmail(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, Collections.singletonList(user.get().getRole())); // здесь не хватает одного параметра(возможно, в этом проблема)

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    }
