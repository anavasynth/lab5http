package com.anavasynth.lab5http.service;
import com.anavasynth.lab5http.model.Item;

import java.util.Collection;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemService
  version 1.0.0
  @since 19.04.2025 - 20.07
*/
public interface ItemService {
    Collection<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Item item);
    void deleteItem(Long id);
}
