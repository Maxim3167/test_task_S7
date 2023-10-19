package app.repository;

import app.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend,Long> {
    List<UserFriend> findUserFriendsByUserId(Long userId);
}
