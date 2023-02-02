package jpabook.japshop;

import hello.Member;
import hello.RoleType;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Random;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Order order = new Order();
            order.addOrderItem(new OrderItem());

//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            em.persist(orderItem);

            System.out.println("===============");
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
