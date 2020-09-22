package ru.geek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geek.persist.entity.Product;
import ru.geek.persist.repo.ProductRepository;
import ru.geek.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "price", required = false) BigDecimal price,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(value = "min-price", required = false) BigDecimal minPrice,
                              @RequestParam(value = "max-price", required = false) BigDecimal maxPrice
    ) {
        LOGGER.info("\nFilter by \nname: {} \ndescription: {} \nprice: {} \nmin-price: {} \nmax-price {}\n", name, description, price, minPrice, maxPrice);

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by("name").and(Sort.by("price")));

        Specification<Product> spec = ProductSpecification.literalTrue();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.nameLike(name));
        }
        if (price != null) {
            spec = spec.and(ProductSpecification.priceLike(price));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(ProductSpecification.descriptionLike(description));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.afterMinPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.beforeMaxPrice(maxPrice));
        }

        model.addAttribute("productsPage", productRepository.findAll(spec, pageRequest));
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) throws SQLException {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) throws SQLException {
        Product product = productRepository.findById(id).get();
        if (id != null) productRepository.delete(product);
        return "redirect:/product";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/order_desc")
    public String orderDesc(Model model) {
        List<Product> products = productRepository.OrderByPriceDesc();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/order_min")
    public String orderMin(Model model) {
        List<Product> products = productRepository.OrderByPrice();
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product";
    }

//    @PostMapping("/create")
//    public String insertProduct(Product product) throws SQLException {
//        productRepository.insert(product);
//        return "redirect:/product";
//    }

//    @PostMapping("/delete")
//    public String deleteProduct(Product product) throws SQLException {
//        productRepository.delete(product);
//        return "redirect:/product";
//    }
}
