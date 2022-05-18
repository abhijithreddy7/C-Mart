package com.abhi.cmart.service.interfaces;

import com.abhi.cmart.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> findAll();

    public void deleteCategory(int id);
    
    public Category findCategoryById(int id);

    public void save(Category category);
}
