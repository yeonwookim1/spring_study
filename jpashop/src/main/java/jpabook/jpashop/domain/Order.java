package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id") //주인 관계
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  //거울 관계
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * persist는 엔티티 당 각각 호출
     * cascade 사용시, persist가 전파되어서 한번만 써줘도 됨
     *
     * persist(orderItemA)
     * persist(orderItemB)
     * persist(order)
     *
     * ->
     * persist(order)
     */

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "delivery_id")   //주인관계
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER,CANCEL]

    //==연관관계 매서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성매서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스로직
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        //return orderItems.stream()
        //                .mapToInt(OrderItem::getTotalPrice)
        //                .sum();
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
