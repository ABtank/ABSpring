package ru.geek.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geek.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findByName(String name);

    List<Product> findByNameLike(String name);

    List<Product> findByPriceLike(BigDecimal price);

    List<Product> OrderByPriceDesc();

    List<Product> findByPriceBetweenOrderByPriceDesc(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> OrderByPrice();

    List<Product> findByNameLikeAndPriceLike(String NameLike, BigDecimal priceLike);

    @Query("from Product u " +
            "where (u.name = :name or :name is null) and" +
            "(u.price = price or :price is null)")
    List<Product> queryByNameLikeAndPriceLike(@Param("name") String NameLike, @Param("price") BigDecimal priceLike);

}
