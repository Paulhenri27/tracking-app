package com.finance.finance.repository;

import com.finance.finance.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    Optional<Category> findByName(String name);
}
