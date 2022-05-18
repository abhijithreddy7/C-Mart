package com.abhi.cmart.service.interfaces;

import com.abhi.cmart.entity.Item;

import java.util.List;

public interface ItemService {

    public List<Item> findAll();

    public Item findItemById(int id);

    public void deleteItem(int id);

    public void save(Item item);
}
