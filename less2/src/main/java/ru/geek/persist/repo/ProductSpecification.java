package ru.geek.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geek.persist.entity.Product;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> literalTrue() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public static Specification<Product> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> descriptionLike(String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Product> priceLike(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<Product> afterMinPrice(BigDecimal minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.gt(root.get("price"), minPrice);
    }

    public static Specification<Product> beforeMaxPrice(BigDecimal maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lt(root.get("price"), maxPrice);
    }
}
