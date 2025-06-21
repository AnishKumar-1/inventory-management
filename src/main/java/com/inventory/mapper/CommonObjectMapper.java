package com.inventory.mapper;


import com.inventory.dto.CategoryDto;
import com.inventory.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonObjectMapper {

    @Autowired
    private ModelMapper modelMapper;



   //generic dynamic method to conver any object to any other
    // source,destination and D return type object
    public <S,D> D convert(S source,Class<D> destination){
        return modelMapper.map(source,destination);
    }
}
