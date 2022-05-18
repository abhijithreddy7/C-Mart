package com.abhi.cmart.service.implementation;

import com.abhi.cmart.dao.ItemRepository;
import com.abhi.cmart.entity.Item;
import com.abhi.cmart.exception.RecordNotFoundException;
import com.abhi.cmart.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findItemById(int id) {
       Optional<Item> result = itemRepository.findById(id);
       Item item = null;
       if(result.isPresent()) {
           item = result.get();
       }
       else {
           throw new RecordNotFoundException("Did not find item id - " + id);
       }
       return item;
    }

    @Override
    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }
}