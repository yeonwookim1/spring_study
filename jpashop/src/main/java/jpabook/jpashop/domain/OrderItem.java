package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id") //주인관계
    private Order order;

    private int orderPrice; //주문 가격
    private int count;      //주문 수량

//    protected OrderItem(){
//        //생성 매서드를 통해서 만들게 하기 위해 접근 제한
//        //@NoArgsConstructor(access = AccessLevel.PROTECTED)
//        //lombok통해서 제한 가능
//    }

    //==생성매서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem= new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스로직
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회로직

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
