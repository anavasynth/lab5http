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
public class ItemServiceImpl {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        return itemRepository.save(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item updateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        return itemRepository.findById(item.getId())
                .map(existingItem -> itemRepository.update(item))
                .orElse(null);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Collection<Item> getAllItems() {
        return itemRepository.findAll();
    }
}