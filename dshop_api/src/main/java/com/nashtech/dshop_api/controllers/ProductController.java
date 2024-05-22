package com.nashtech.dshop_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController{
    
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok()
                            .body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok()
                            .body(product);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductCreateUpdateRequest productCreateRequest) {
        var product = productService.createProduct(productCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, 
                                                @Valid @RequestBody ProductCreateUpdateRequest productCreateRequest) {
        var product = productService.updateProduct(id, productCreateRequest);
        return ResponseEntity.ok()
                            .body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok()
                            .body(DELETE_SUCCESS_MSG);
    }
}
