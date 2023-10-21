package app.service;

import app.dto.UserFriendReadDto;
import app.entity.UserFriend;
import app.mapper.UserFriendReadMapper;
import app.repository.UserFriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFriendService {
    private final UserFriendReadMapper userFriendReadMapper;
    private final UserFriendRepository userFriendRepository;

    public List<UserFriendReadDto> findAllFriendsById(Long id) {
        if(id == null){
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
