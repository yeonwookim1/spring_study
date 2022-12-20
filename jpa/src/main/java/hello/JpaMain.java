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
            Member member = new Member(12L, "kyw");

            //수정
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println(findMember.getName());
//            //persist를 하지 않아도 값이 변경된다.
//            findMember.setName("spirng_jpa");

            //jpql
            //객체를 대상으로 쿼리
//            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
////                    .setFirstResult(5)
////                    .setMaxResults(10)
//                    .getResultList();
//
//            for(Member m : resultList){
//                System.out.println(m.getName());
//            }


            //비영속
//            Member member1 = new Member(27L, "kyw11");
//
//            //영속
//            em.persist(member1);

//            Member findMember1 = em.find(Member.class, 27L);
//            Member findMember2 = em.find(Member.class, 27L);
//            System.out.println("result = " + (findMember1 == findMember2)); //true

            //commit
//            Member memberC1 = new Member(150L,"A");
//            Member memberC2 = new Member(151L,"B");
//
//            em.persist(memberC1);
//            em.persist(memberC2);


            Member fMember = new Member(200L,"FLUSH");
            em.persist(fMember);
            em.flush();
            System.out.println("flush");


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
