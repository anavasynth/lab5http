package com.anavasynth.lab5http.controller;

import com.anavasynth.lab5http.model.Item;
import org.springframework.web.bind.annotation.*;

import com.anavasynth.lab5http.model.Item;
import com.anavasynth.lab5http.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public Collection<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping("/")
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/")
    public Item updateItem(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
