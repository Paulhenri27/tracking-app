package com.finance.finance.service;

import com.finance.finance.model.Category;
import com.finance.finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;


    public Category addCategory(Category category)
    {
        return categoryRepository.save(category);
    }
}
