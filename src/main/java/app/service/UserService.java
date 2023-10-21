package app.service;

import app.dto.CreateUserDto;
import app.dto.UserFriendCreateDto;
import app.dto.UserFriendReadDto;
import app.dto.UserReadDto;
import app.entity.User;
import app.entity.UserFriend;
import app.mapper.CreateUserDtoMapper;
import app.mapper.UserFriendCreateDtoMapper;
import app.mapper.UserFriendReadMapper;
import app.mapper.UserReadMapper;
import app.repository.UserFriendRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserFriendCreateDtoMapper userFriendCreateDtoMapper;
    private final UserFriendReadMapper userFriendReadMapper;

    @Transactional
    public UserReadDto saveUser(CreateUserDto createUserDto) {
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
    public void addFriend(UserFriendCreateDto userFriendDto, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            log.error("Пользователя с id " + userId + " не сущесвует");
            throw new RuntimeException("Пользователя с id " + userId + " не сущесвует");
        }
        Optional.of(userFriendDto)
                .map(userFriendCreateDtoMapper::map)
                .map(userFriendRepository::save)
                .ifPresent(userFriend -> userFriend.setUser(user.get()));
        log.info("Пользователь " + userFriendDto.firstName() + " " + userFriendDto.lastName() + " добавлен в друзья");
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
}
