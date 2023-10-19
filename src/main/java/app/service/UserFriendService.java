package app.service;

import app.dto.UserFriendReadDto;
import app.mapper.UserFriendReadMapper;
import app.repository.UserFriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFriendService {
    private final UserFriendReadMapper userFriendReadMapper;
    private final UserFriendRepository userFriendRepository;


    public List<UserFriendReadDto> findAllFriendsById(Long id) {
        if(id == null){
            throw new RuntimeException("Id пользователя не был передан");
        }
        return userFriendRepository.findUserFriendsByUserId(id)
                .stream()
                .map(userFriendReadMapper::map)
                .toList();
    }
}
