package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itmItemRepository;

    @Transactional
    public void saveItem(Item item){
        itmItemRepository.save(item);
    }

    public List<Item> findItem(){
        return itmItemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itmItemRepository.findOne(itemId);
    }
}
