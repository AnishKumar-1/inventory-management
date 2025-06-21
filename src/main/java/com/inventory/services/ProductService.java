package com.inventory.services;

import com.inventory.dto.ProductDto;
import com.inventory.exceptions.DataNotFound;
import com.inventory.mapper.ProductMapper;
import com.inventory.models.Category;
import com.inventory.models.Product;
import com.inventory.repository.CategoryRepo;
import com.inventory.repository.ProductRepo;
import com.inventory.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private Helper helper;
    @Autowired
    private ProductMapper productMapper;


    //create product
    public ResponseEntity<Object> createProduct(ProductDto productDto,Long category_id){
        Optional<Category> category=categoryRepo.findById(category_id);
        if(category.isEmpty()){
            throw new IllegalArgumentException("category not found with this id: "+category_id);
        }
       Optional<Product> existsProduct=productRepo.findByProductName(productDto.getProductName());
       if(existsProduct.isPresent()){
           throw new DataNotFound("product already exists with this name: "+productDto.getProductName());
       }

       Product product=helper.product(productDto);
       product.setCategory(category.get());

      Product result=productRepo.save(product);
      ProductDto response=helper.productDto(result);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    //get productId
    public ResponseEntity<Object> productById(Long productId){
      Optional<Product> product= productRepo.findById(productId);
      if(product.isEmpty()){
          throw new IllegalArgumentException("Product not exists with this id: "+productId);
      }
      Product existsProduct=product.get();
      ProductDto response=helper.productDto(existsProduct);
      return ResponseEntity.ok(response);
    }


    //delete product by id
    public ResponseEntity<String> deleteProduct(Long productId){
        if(!productRepo.existsById(productId)){
            throw new IllegalArgumentException("product not found with this id: "+productId);
        }

        productRepo.deleteById(productId);
        return ResponseEntity.ok("product deleted successfully");
    }

    public ResponseEntity<String> updateProduct(ProductDto productDto,Long productId){
        Optional<Product> existsUser=productRepo.findById(productId);
        if(existsUser.isEmpty()){
            throw new IllegalArgumentException("product not found with this id: "+productId);
        }
        Product product=existsUser.get();
        productMapper.updateProductFromDto(productDto,product);
        productRepo.save(product);
        return ResponseEntity.ok("product updated successfully");

    }

    public ResponseEntity<List<ProductDto>> listProduct(){
      List<ProductDto> response=productRepo.findAll().stream().map(
              product->helper.productDto(product)
      ).toList();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.ok(response);
    }




}