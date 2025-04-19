package com.anavasynth.lab5http.controller;

import com.anavasynth.lab5http.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemController
  version 1.0.0
  @since 19.04.2025 - 19.16
*/

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private Map<Long, Item> itemMap = new HashMap<>();
    private Long idCounter = 1L;

    @GetMapping("/")
    public Collection<Item> getAllItems() {
        return itemMap.values();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemMap.get(id);
    }

    @PostMapping("/")
    public Item createItem(@RequestBody Item item) {
        item.setId(idCounter++);
        itemMap.put(item.getId(), item);
        return item;
    }

    @PutMapping("/")
    public Item updateItem(@RequestBody Item updatedItem) {
        itemMap.put(updatedItem.getId(), updatedItem);
        return updatedItem;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemMap.remove(id);
    }
}
