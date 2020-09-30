package ru.geek.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geek.controller.NotFoundException;
import ru.geek.persist.entity.Product;
import ru.geek.persist.repo.ProductRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductResource {

    private final ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll (){
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById (@PathVariable("id") int id){
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping (consumes = "application/json", produces = "application/json")
    public Product createProduct (@RequestBody Product product){
        if(product.getId()!=null){
            throw new IllegalArgumentException("id found in the create product");
        }
        productRepository.save(product);
        return product;
    }

    @PutMapping  (consumes = "application/json", produces = "application/json")
    public Product upduteProduct (@RequestBody Product product){
        return productRepository.save(product);
    }

    @DeleteMapping (path = "/{id}/id", produces = "application/json")
    public void deleteProguct (@PathVariable("id") int id){
        productRepository.deleteById(id);
    }
}
