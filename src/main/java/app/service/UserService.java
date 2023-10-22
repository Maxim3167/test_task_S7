package app.service;

import app.dto.CreateUserDto;
import app.dto.UserFriendReadDto;
import app.dto.UserReadDto;
import app.entity.User;
import app.entity.UserFriend;
import app.mapper.CreateUserDtoMapper;
import app.mapper.UserFriendReadMapper;
import app.mapper.UserReadMapper;
import app.repository.UserFriendRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService  {
    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;
    private final CreateUserDtoMapper createUserDtoMapper;
    private final UserReadMapper userReadMapper;
    private final UserFriendReadMapper userFriendReadMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserReadDto saveUser(CreateUserDto createUserDto) {
        if(!checkDuplicateUser(createUserDto) || userRepository.findUserByEmail(createUserDto.email()).isPresent()){
            log.error("Пользователь с таким логином или паролем уже существует");
            throw new RuntimeException("Пользователь с таким логином или паролем уже существует");
        }
       return Optional.of(createUserDto)
                .map(createUserDtoMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow(() -> {
                    log.error("Ошибка при сохранении пользователя");
                    return new RuntimeException("Ошибка при сохранении пользователя");
                });
    }

    @Transactional
    public void addFriend(Long userId, Long friendId){
        Optional<User> maybeFriend = userRepository.findById(friendId);
        User user = userRepository.findById(userId).get();

        UserFriend userFriend = UserFriend
                .builder()
                .firstName(maybeFriend.get().getPersonalInfo().getFirstName())
                .lastName(maybeFriend.get().getPersonalInfo().getLastName())
                .build();
        Optional.of(userFriend)
                .map(userFriendRepository::save)
                .ifPresent(friend -> userFriend.setUser(user));
        log.info("Пользователь " + user.getPersonalInfo().getFirstName() + " " + user.getPersonalInfo().getLastName() + " добавлен в друзья");
    }

    @Transactional
    public void deleteFriend(Long friendId){
        Optional<UserFriend> friend = userFriendRepository.findById(friendId);
        userFriendRepository.deleteById(friendId);
        log.info("Пользователь " + friend.get().getFirstName() + " удалён из друзей");
    }


    public List<UserFriendReadDto> findFriends(String firstName,String lastName) {
        return userFriendRepository.findUserFriendsByFilter(firstName,lastName)
                .stream()
                .map(userFriendReadMapper::map)
                .toList();
    }

    public List<UserFriendReadDto> findAllFriendsById(Long id) {
        if(id == null){
            log.error("Id пользователя не был передан");
            throw new RuntimeException("Id пользователя не был передан");
        }
        List<UserFriend> userFriendsByUserId = userFriendRepository.findUserFriendsByUserId(id);
        if(userFriendsByUserId.isEmpty()){
            log.warn("Друзья не найдены");
            throw new RuntimeException("Список друзей пользователя пуст");
        }
        return  userFriendsByUserId
                .stream()
                .map(userFriendReadMapper::map)
                .toList();
    }

    private boolean checkDuplicateUser(CreateUserDto createUserDto){
        return userRepository.findAllPasswords()
                .stream()
                .filter(encodePassword -> passwordEncoder.matches(createUserDto.password(),encodePassword))
                .findFirst()
                .isEmpty();
    }
}
