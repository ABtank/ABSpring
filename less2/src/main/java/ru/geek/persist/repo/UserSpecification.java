package ru.geek.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geek.persist.entity.User;

public final class UserSpecification {

    public static Specification trueLiteral(){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
    }

    public static Specification<User> loginLike(String login){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("login"), "%"+login+"%"));
    }

    public static Specification<User> emailLike(String email){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%"+email+"%"));
    }
}
