package com.abhi.cmart.service;

import com.abhi.cmart.dao.CategoryRepository;
import com.abhi.cmart.entity.Category;
import com.abhi.cmart.service.interfaces.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void ShouldReturnAllCategoriesWhenHasCategoriesTest() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"Action"));
        categories.add(new Category(2,"Adventure"));
        when(categoryRepository.findAll()).thenReturn(categories);
        assertEquals(categories, categoryService.findAll());
    }

    @Test
    void ShouldReturnNullWhenHasNoCategoriesTest() {
        List<Category> categories = null;
        when(categoryRepository.findAll()).thenReturn(categories);
        assertEquals(categories, categoryService.findAll());
    }

    @Test
    void ShouldReturnCategoryWhenGivenExistingIdTest() {
        Category category = new Category(1,"Action");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        assertEquals(category, categoryService.findCategoryById(1));
    }

    @Test
    void ShouldThrowExceptionWhenGivenNonExistingIdTest() throws RuntimeException{
        Optional<Category> category = Optional.empty();
        when(categoryRepository.findById(2)).thenReturn(category);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.findCategoryById(2);
        });
        assertEquals("Did not find Category Id - " + 2, thrown.getMessage());
    }

    @Test
    void ShouldSaveGivenCategoryTest() {
        Category category = new Category(2, "Adventure");
        categoryService.save(category);
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    void ShouldDeleteGivenCategoryTest() {
        categoryService.deleteCategory(2);
        verify(categoryRepository,times(1)).deleteById(2);
    }
}