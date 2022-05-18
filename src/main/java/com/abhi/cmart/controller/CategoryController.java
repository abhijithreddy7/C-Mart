package com.abhi.cmart.controller;

import com.abhi.cmart.entity.Category;
import com.abhi.cmart.entity.Item;
import com.abhi.cmart.service.interfaces.CategoryService;
import com.abhi.cmart.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private ItemService itemService;

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("admin/categories/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId, Model model) {
        Category category = categoryService.findCategoryById(categoryId);
        if(category == null) {
            throw new RuntimeException("Category id not found - " + categoryId);
        }
        categoryService.deleteCategory(categoryId);
        return fetchAllItemsAndCategories(model);
    }

    @PostMapping("/admin/submitCategory")
    public String submitCategory(@ModelAttribute("newCategory") Category category, Model model) {
        category.setId(0);
        categoryService.save(category);
        return fetchAllItemsAndCategories(model);
    }

    @PostMapping("/admin/addCategoryForm")
    public String addCategory(Model model){
        Category category = new Category();
        model.addAttribute("newCategory", category);
        return "category-form";
    }

    @GetMapping("/categories/{categoryId}")
    public String categoryList(@PathVariable int categoryId, Model model) {
        return getCategoryList(categoryId, model);
    }

    @PostMapping("/categories/{categoryId}")
    public String getCategoryList(@PathVariable int categoryId, Model model) {
        Category category = categoryService.findCategoryById(categoryId);
        List<Item> itemsList = category.getItems();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("items", itemsList);
        model.addAttribute("categoryId", categoryId);
        return "category-items";
    }

    public String fetchAllItemsAndCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Item> itemList = itemService.findAll();
        model.addAttribute("items", itemList);
        model.addAttribute("categoryId", null);
        return "category-items";
    }
}