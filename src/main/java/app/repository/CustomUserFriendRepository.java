package app.repository;

import app.entity.UserFriend;

import java.util.List;

public interface CustomUserFriendRepository {
    List<UserFriend> findUserFriendsByFilter(String firstName,String lastName);

}
