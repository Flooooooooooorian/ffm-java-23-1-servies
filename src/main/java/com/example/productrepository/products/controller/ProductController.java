package com.example.productrepository.products.controller;

import com.example.productrepository.products.models.NewProduct;
import com.example.productrepository.products.models.Product;
import com.example.productrepository.products.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody NewProduct newProduct) {
        return productService.addProduct(newProduct);
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id);
    }

    @PutMapping("{id}")
    public Product putProduct(@PathVariable String id, @RequestBody NewProduct product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(Exception exception) {
        return "Ups, hier ist etwas schief gelaufen..." + exception.getMessage();
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String handleIndexOutOfBoundException(IndexOutOfBoundsException exception) {
        return "Fehler";
    }
}
