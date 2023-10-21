package app.mapper;

import app.dto.UserFriendCreateDto;
import app.entity.UserFriend;
import org.springframework.stereotype.Component;

@Component
public class UserFriendCreateDtoMapper implements Mapper<UserFriendCreateDto, UserFriend>{
    @Override
    public UserFriend map(UserFriendCreateDto userDto) {
        return UserFriend.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .build();
    }
}
