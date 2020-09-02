package ru.geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geek.persistance.Product;
import ru.geek.persistance.ProductRepository;
import ru.geek.persistance.User;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProducts = productRepository.getAllProducts();
        model.addAttribute("products", allProducts);
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) throws SQLException {
        Product product = productRepository.findById(id);
        if (id != null) productRepository.delete(product);
        return "redirect:/product";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) throws SQLException {
        if (product.getId() != null) {
            productRepository.update(product);
        } else {
            productRepository.insert(product);
        }
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
