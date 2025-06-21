package com.inventory.controllers;

import com.inventory.dto.ProductDto;
import com.inventory.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create product
    @PostMapping("/{category_id}")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDto productDto
    ,@NotNull(message="category id is required") @PathVariable Long category_id){
        return productService.createProduct(productDto,category_id);
    }

    //get product by id
    @GetMapping("/{product_id}")
    public ResponseEntity<Object> productById(@NotNull(message = "product id is required") @PathVariable Long product_id){
        return productService.productById(product_id);
    }

    //delete product by id
    @DeleteMapping("/{product_id}")
    public ResponseEntity<String> deleteProduct(@NotNull(message = "product id is required") @PathVariable Long product_id){
        return productService.deleteProduct(product_id);
    }

    //update product by id
    @PatchMapping("/{product_id}")
    public ResponseEntity<String> updateProduct(@NotNull(message = "product id is required")
                                                @PathVariable Long product_id,@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto,product_id);
    }

    //get all product
    @GetMapping
    public ResponseEntity<List<ProductDto>> products(){
        return productService.listProduct();
    }
}
