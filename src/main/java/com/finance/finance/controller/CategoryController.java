package com.finance.finance.controller;


import com.finance.finance.dto.CategoryDTO;
import com.finance.finance.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    public CategoryController(CategoryService categoryService)
//    {
//        this.categoryService = categoryService;
//    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories()
    {
        List<CategoryDTO> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
