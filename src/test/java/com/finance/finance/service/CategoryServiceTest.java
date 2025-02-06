package com.finance.finance.service;

import com.finance.finance.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest
{
    @Autowired
    private CategoryService categoryService;


    @Test
    void addCategory()
    {
        Category category = new Category();
        category.setName("Spending");

        System.out.println(categoryService.addCategory(category));
    }

}