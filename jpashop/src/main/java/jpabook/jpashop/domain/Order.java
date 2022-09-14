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
}
