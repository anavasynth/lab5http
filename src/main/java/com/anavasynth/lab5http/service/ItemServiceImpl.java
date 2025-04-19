package com.anavasynth.lab5http.service;

import com.anavasynth.lab5http.model.Item;
import com.anavasynth.lab5http.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemServiceImpl
  version 1.0.0
  @since 19.04.2025 - 20.08
*/

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private Long idCounter = 1L;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Collection<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item createItem(Item item) {
        Long newId = idCounter++;
        Item newItem = new Item(newId, item.getName(), item.getCode(), item.getDescription());
        return itemRepository.save(newItem);
    }

    @Override
    public Item updateItem(Item item) {
        // Перевірка, чи існує такий елемент
        if (itemRepository.findById(item.getId()).isPresent()) {
            Item updatedItem = new Item(item.getId(), item.getName(), item.getCode(), item.getDescription());
            return itemRepository.update(updatedItem);
        } else {
            return null; // або кидати виняток
        }
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}