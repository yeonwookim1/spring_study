package hello;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //삽입
//            Member member = new Member();
//            member.setId(12L);
//            member.setName("kyw");
//            em.persist(member);

            //수정
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println(findMember.getName());

            //persist를 하지 않아도 값이 변경된다.
            findMember.setName("spirng_jpa");

            //jpql
            //객체를 대상으로 쿼리
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(10)
                    .getResultList();

            for(Member m : resultList){
                System.out.println(m.getName());
            }


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
