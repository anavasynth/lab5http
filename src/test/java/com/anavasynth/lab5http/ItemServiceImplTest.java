package com.anavasynth.lab5http;


import com.anavasynth.lab5http.model.Item;
import com.anavasynth.lab5http.service.ItemServiceImpl;
import com.anavasynth.lab5http.repository.ItemRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  ItemServiceImplTest
  version 1.0.0
  @since 04.05.2025 - 23.01
*/

class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Captor
    private ArgumentCaptor<Item> itemCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewItem() {
        Item input = new Item(null, "Test", "C1", "Description");
        Item saved = new Item(1L, "Test", "C1", "Description");
        given(itemRepository.save(any(Item.class))).willReturn(saved);

        Item result = itemService.createItem(input);

        verify(itemRepository).save(itemCaptor.capture());
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnItemById() {
        Item item = new Item(1L, "Name", "Code", "Desc");
        given(itemRepository.findById(1L)).willReturn(Optional.of(item));

        Item result = itemService.getItemById(1L);

        assertThat(result).isEqualTo(item);
    }

    @Test
    void shouldReturnNullWhenItemNotFound() {
        given(itemRepository.findById(2L)).willReturn(Optional.empty());

        Item result = itemService.getItemById(2L);

        assertThat(result).isNull();
    }

    @Test
    void shouldUpdateItemIfExists() {
        Item item = new Item(1L, "Name", "Code", "Updated");
        given(itemRepository.findById(1L)).willReturn(Optional.of(item));
        given(itemRepository.update(any(Item.class))).willReturn(item);

        Item result = itemService.updateItem(item);

        assertThat(result.getDescription()).isEqualTo("Updated");
    }

    @Test
    void shouldReturnNullIfUpdateItemNotExists() {
        Item item = new Item(999L, "X", "Y", "Z");
        given(itemRepository.findById(999L)).willReturn(Optional.empty());

        Item result = itemService.updateItem(item);

        assertThat(result).isNull();
    }

    @Test
    void shouldDeleteItem() {
        itemService.deleteItem(10L);

        verify(itemRepository).deleteById(10L);
    }

    @Test
    void shouldReturnAllItems() {
        List<Item> items = List.of(
                new Item(1L, "A", "C1", "D1"),
                new Item(2L, "B", "C2", "D2")
        );
        given(itemRepository.findAll()).willReturn(items);

        Collection<Item> result = itemService.getAllItems();

        assertThat(result).hasSize(2);
    }

    @Test
    void createItemShouldIncrementIdCounter() {
        Item item = new Item(null, "X", "Y", "Z");
        given(itemRepository.save(any(Item.class))).willReturn(item);

        itemService.createItem(item);
        itemService.createItem(item);

        verify(itemRepository, times(2)).save(any(Item.class));
    }

    @Test
    void getAllItemsShouldCallFindAllOnce() {
        itemService.getAllItems();
        verify(itemRepository).findAll();
    }

    @Test
    void getItemByIdShouldCallFindById() {
        Long id = 123L;
        itemService.getItemById(id);
        verify(itemRepository).findById(id);
    }

    @Test
    void deleteItemShouldCallDeleteById() {
        Long id = 42L;
        itemService.deleteItem(id);
        verify(itemRepository, times(1)).deleteById(id);
    }

    @Test
    void updateShouldOnlyCallUpdateIfItemExists() {
        Item item = new Item(1L, "Name", "Code", "Desc");
        given(itemRepository.findById(1L)).willReturn(Optional.of(item));
        itemService.updateItem(item);

        verify(itemRepository).update(any(Item.class));
    }

    @Test
    void updateShouldNotCallUpdateIfItemDoesNotExist() {
        Item item = new Item(99L, "No", "Code", "None");
        given(itemRepository.findById(99L)).willReturn(Optional.empty());

        itemService.updateItem(item);

        verify(itemRepository, never()).update(any(Item.class));
    }

    @Test
    void createItemShouldPreserveFields() {
        Item input = new Item(null, "Preserve", "PR1", "TestDesc");
        given(itemRepository.save(any(Item.class))).willAnswer(invocation -> invocation.getArgument(0));

        Item result = itemService.createItem(input);

        assertThat(result.getName()).isEqualTo("Preserve");
        assertThat(result.getCode()).isEqualTo("PR1");
    }

    @Test
    void updateItemShouldReturnSameId() {
        Item item = new Item(5L, "N", "C", "D");
        given(itemRepository.findById(5L)).willReturn(Optional.of(item));
        given(itemRepository.update(any(Item.class))).willReturn(item);

        Item result = itemService.updateItem(item);

        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void shouldSupportMultipleCreates() {
        Item base = new Item(null, "Test", "Code", "Desc");
        given(itemRepository.save(any(Item.class))).willReturn(base);

        itemService.createItem(base);
        itemService.createItem(base);
        itemService.createItem(base);

        verify(itemRepository, times(3)).save(any(Item.class));
    }

    @Test
    void shouldNotCrashOnDeleteUnknownId() {
        willDoNothing().given(itemRepository).deleteById(anyLong());
        itemService.deleteItem(999L);
        verify(itemRepository).deleteById(999L);
    }

    @Test
    void getAllItemsReturnsEmptyListWhenNoItems() {
        given(itemRepository.findAll()).willReturn(Collections.emptyList());

        Collection<Item> result = itemService.getAllItems();

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUpdateItemWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.updateItem(null);
        });

        verify(itemRepository, never()).findById(anyLong());
        verify(itemRepository, never()).update(any(Item.class));
    }

    @Test
    void shouldThrowExceptionWhenCreateItemWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.createItem(null);
        });

        verify(itemRepository, never()).save(any(Item.class));
    }
}
