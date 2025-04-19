package com.anavasynth.lab5http.repository;

import com.anavasynth.lab5http.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemRepository
  version 1.0.0
  @since 19.04.2025 - 20.09
*/

@Repository
public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
    Collection<Item> findAll();
    Item update(Item item);
    void deleteById(Long id);
}
