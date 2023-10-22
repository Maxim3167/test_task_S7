package app.controller;

import app.dto.*;
import app.entity.User;
import app.repository.UserRepository;
import app.security.jwt.JwtTokenProvider;
import app.service.UserService;
import app.util.StringErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registerUser(@RequestBody @Validated CreateUserDto createUserDto,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorString = StringErrorBuilder.buildErrorString(bindingResult);
            throw new ValidationException(errorString);
        }
        return userService.saveUser(createUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationRequestDto dto,
                                BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                String errorString = StringErrorBuilder.buildErrorString(bindingResult);
                throw new ValidationException(errorString);
            }
            String username = dto.email();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.password()));
            Optional<User> user = userRepository.findUserByEmail(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, Collections.singletonList(user.get().getRole()));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/addFriend")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriend(//@RequestBody UserFriendCreateDto userFriendDto,
                          @RequestParam Long friendId,
                          @RequestParam Long userId,
                          @RequestHeader String token){
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("/deleteFriend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriend(@RequestParam Long friendId,
                             @RequestHeader String token){
        userService.deleteFriend(friendId);
    }

    @GetMapping("/findFriends")
    public List<UserFriendReadDto> findFriends(@RequestParam(required = false) String firstName,
                                               @RequestParam(required = false) String lastName,
                                               @RequestHeader String token){
        return userService.findFriends(firstName,lastName);
    }

    @GetMapping
    public List<UserFriendReadDto> findAllFriends(@RequestParam Long id,
                                                  @RequestHeader String token){
        return userService.findAllFriendsById(id);
    }
}
