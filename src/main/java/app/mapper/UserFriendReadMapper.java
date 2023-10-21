package app.mapper;

import app.dto.UserFriendReadDto;
import app.entity.UserFriend;
import org.springframework.stereotype.Component;

@Component
public class UserFriendReadMapper implements Mapper<UserFriend, UserFriendReadDto>{
    @Override
    public UserFriendReadDto map(UserFriend userFriend) {
        return UserFriendReadDto
                .builder()
                .lastName(userFriend.getLastName())
                .firstName(userFriend.getFirstName())
                .build();
    }
}
