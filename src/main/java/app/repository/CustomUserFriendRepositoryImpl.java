package app.repository;

import app.entity.UserFriend;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class CustomUserFriendRepositoryImpl implements CustomUserFriendRepository {
    private final EntityManager entityManager;
    @Override
    public List<UserFriend> findUserFriendsByFilter(String firstName,String lastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserFriend> criteria = cb.createQuery(UserFriend.class);
        Root<UserFriend> root = criteria.from(UserFriend.class);
        criteria.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if(firstName != null && !firstName.equals("")){
            predicates.add(cb.like(root.get("firstName"),firstName));
        }
        if(lastName != null && !lastName.equals("")){
            predicates.add(cb.like(root.get("lastName"),lastName));
        }
        criteria.where(predicates.toArray(Predicate[]::new));// передаем список всех наших условий
        criteria.orderBy(cb.asc(root.get("id")));

        return entityManager.createQuery(criteria).getResultList()
                .stream()
                .toList();
    }
}
