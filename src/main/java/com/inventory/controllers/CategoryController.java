package com.inventory.controllers;

import com.inventory.dto.CategoryDto;
import com.inventory.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create category
    @PostMapping
    public ResponseEntity<Object> createCategory(@Valid @RequestBody List<CategoryDto> categoryDto){
        return categoryService.createCategory(categoryDto);
    }

    //get category by id
    @GetMapping("/{category_id}")
    public ResponseEntity<Object> categoryById(@NotNull(message = "Category ID is required") @PathVariable Long category_id){
        return categoryService.categoryById(category_id);
    }

    //all category
    @GetMapping
    public ResponseEntity<Object> allCategory(){
        return categoryService.allCategories();
    }

    //delete category by id
    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@NotNull(message = "Category ID is required")
                                                 @PathVariable Long category_id) {
        return categoryService.deleteCategory(category_id);
    }

    //patch category by data and id
    @PatchMapping("{category_id}")
    public ResponseEntity<Object> patchCategoryById(@RequestBody CategoryDto categoryDto,@PathVariable
                                                   @NotNull(message = "Category ID is required") Long category_id){
      return categoryService.patchCategory(categoryDto,category_id);
    }
}
