package com.anavasynth.lab5http.repository;

import com.anavasynth.lab5http.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemRepositoryImpl
  version 1.0.0
  @since 19.04.2025 - 20.09
*/

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, Item> itemStorage = new HashMap<>();

    @Override
    public Item save(Item item) {
        itemStorage.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(itemStorage.get(id));
    }

    @Override
    public Collection<Item> findAll() {
        return itemStorage.values();
    }

    @Override
    public Item update(Item item) {
        itemStorage.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteById(Long id) {
        itemStorage.remove(id);
    }
}
