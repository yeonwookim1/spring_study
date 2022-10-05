package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
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

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        //영속 상태
        //변경 감지
        //merge로 할 시 null이 업데이트가 될 수도 있기에
        Item findItem = itmItemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
    public List<Item> findItem(){
        return itmItemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itmItemRepository.findOne(itemId);
    }
}
