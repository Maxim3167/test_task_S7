package app.controller;

import app.dto.CreateUserDto;
import app.dto.UserFriendReadDto;
import app.dto.UserReadDto;
import app.service.UserFriendService;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFriendService userFriendService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registerUser(@RequestBody CreateUserDto createUserDto){
       return userService.saveUser(createUserDto);
    }


    @GetMapping("/friends")
    public List<UserFriendReadDto> findAllFriends(@RequestParam Long id){
        return userFriendService.findAllFriendsById(id);
    }


}
