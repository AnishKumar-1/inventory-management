package com.inventory.services;

import com.inventory.dto.CategoryDto;
import com.inventory.exceptions.DataNotFound;
import com.inventory.mapper.CommonObjectMapper;
import com.inventory.models.Category;
import com.inventory.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CommonObjectMapper commonObjectMapper;

    //create category
    public ResponseEntity<Object> createCategory(List<CategoryDto> categoryDto) {
        List<Category> toSave = categoryDto.stream()
                .filter(dto -> categoryRepo.findByCategoryName(dto.getCategoryName()).isEmpty())
                .map(dto -> commonObjectMapper.convert(dto, Category.class))
                .collect(Collectors.toList());

        List<Category> saved = categoryRepo.saveAll(toSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    //category by id
    public ResponseEntity<Object> categoryById(Long category_id){
        Optional<Category> category=categoryRepo.findById(category_id);
        if(category.isEmpty()){
            throw new DataNotFound("category not found with this id: "+category_id);
        }
        CategoryDto categoryDto=commonObjectMapper.convert(category,CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    //all category
    public ResponseEntity<Object> allCategories() {
        List<Category> cat = categoryRepo.findAll();

        if (cat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "category not available"));
        }

        List<CategoryDto> dtoList = cat.stream()
                .map(c -> commonObjectMapper.convert(c, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getCategory_id))
                .toList();

        return ResponseEntity.ok(Map.of("categories", dtoList));
    }

    //delete category by id
    public ResponseEntity<String> deleteCategory(Long category_id){
        if(!categoryRepo.existsById(category_id)){
            throw new IllegalArgumentException("category not found with this id: "+category_id);
        }
        categoryRepo.deleteById(category_id);
        return ResponseEntity.ok("category deleted successfully.");
    }
    //patch category by id
    public ResponseEntity<Object> patchCategory(CategoryDto categoryDto,Long category_id){
       Optional<Category> existsCategory=categoryRepo.findById(category_id);

       if(existsCategory.isEmpty()){
           throw new DataNotFound("category not found with this id");
       }
        Category result=existsCategory.get();
       if(!categoryDto.getCategoryName().isEmpty()){
           result.setCategoryName(categoryDto.getCategoryName());
       }
       categoryRepo.save(result);
       return ResponseEntity.ok("category updated successfully.");
    }
}
