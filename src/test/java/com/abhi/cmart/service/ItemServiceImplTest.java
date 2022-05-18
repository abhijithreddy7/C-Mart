package com.abhi.cmart.service;

import com.abhi.cmart.dao.ItemRepository;
import com.abhi.cmart.entity.Category;
import com.abhi.cmart.entity.Item;
import com.abhi.cmart.service.interfaces.ItemService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @MockBean
    ItemRepository itemRepository;

    @Test
    void ShouldReturnAllItemsWhenHasItemsTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        when(itemRepository.findAll()).thenReturn(items);
        assertEquals(items, itemService.findAll());
    }

    @Test
    void ShouldReturnNullWhenHasNoItemsTest() {
        List<Item> items = null;
        when(itemRepository.findAll()).thenReturn(items);
        assertEquals(items, itemService.findAll());
    }

    @Test
    void ShouldReturnItemWhenGivenExistingIdTest() {
        Category category = new Category(1, "Action");
        Item item = new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category);
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        assertEquals(item, itemService.findItemById(1));
    }

    @Test
    void ShouldThrowExceptionWhenGivenNonExistingIdTest() throws RuntimeException{
        Optional<Item> item = Optional.empty();
        when(itemRepository.findById(2)).thenReturn(item);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            itemService.findItemById(2);
        });
        assertEquals("Did not find item id - " + 2, thrown.getMessage());
    }

    @Test
    void ShouldSaveGivenCategoryTest() {
        Category category = new Category(1, "Action");
        Item item = new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category);
        itemService.save(item);
        verify(itemRepository,times(1)).save(item);
    }

    @Test
    void ShouldDeleteGivenCategoryTest() {
        itemService.deleteItem(1);
        verify(itemRepository,times(1)).deleteById(1);
    }

}