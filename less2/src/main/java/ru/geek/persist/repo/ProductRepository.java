package ru.geek.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geek.persist.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Integer> {

    List<Product> findByName(String name);

    List<Product> findByNameLike(String name);
}
