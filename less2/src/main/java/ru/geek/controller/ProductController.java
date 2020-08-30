package ru.geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("products",allProducts);
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product_update";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product_delete";
    }

    @GetMapping("/create/{id}")
    public String createProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product_create";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) throws SQLException {
        productRepository.update(product);
        return "redirect:/product";
    }

    @PostMapping("/create")
    public String insertProduct(Product product) throws SQLException {
        productRepository.insert(product);
        return "redirect:/product";
    }

    @PostMapping("/delete")
    public String deleteProduct(Product product) throws SQLException {
        productRepository.delete(product);
        return "redirect:/product";
    }
}
