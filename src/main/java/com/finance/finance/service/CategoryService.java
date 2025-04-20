package com.finance.finance.service;

import com.finance.finance.dto.CategoryDTO;
import com.finance.finance.mapper.Mapper;
import com.finance.finance.model.Category;
import com.finance.finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;


    public Category addCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    public List<CategoryDTO> getCategories()
    {
        List<Category> category = categoryRepository.findAll();

        return category.stream().map(Mapper::mapCategoryToDTO).collect(Collectors.toList());
    }
}
