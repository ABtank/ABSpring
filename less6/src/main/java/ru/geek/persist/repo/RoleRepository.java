package ru.geek.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geek.persist.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
