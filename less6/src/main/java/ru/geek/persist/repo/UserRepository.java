package ru.geek.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geek.persist.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer>, JpaSpecificationExecutor<User> {

    User findByLogin(String login);

    List<User> findByLoginLike(String loginPattern);

    List<User> findByEmailLike(String email);

    List<User> findByEmailLikeAndLoginLike(String login, String email);

    @Query("FROM User u "+
    "WHERE (u.email = :email or :email is null) and"+
            "(u.login = :login or :login is null)")
    List<User> queryByEmailLikeAndLoginLike(@Param("login") String login, @Param("email") String email);


}
