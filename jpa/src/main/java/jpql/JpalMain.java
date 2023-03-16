package jpql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class JpalMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JMember member = new JMember();
            String memberName = "member"  + new Date().getTime();
            member.setName(memberName);
            member.setAge(20);
            em.persist(member);

            //반환타입이 명확
            TypedQuery<JMember> query = em.createQuery("select m from JMember m", JMember.class);
            TypedQuery<String> nameQuery = em.createQuery("select m.name from JMember m", String.class);

            //반환타입이 불명확(여러개일 때)
            Query query1 = em.createQuery("select m.name, m.age from JMember m");


            List<JMember> resultList = query.getResultList();
            for(JMember jm : resultList){
                System.out.println("jm.getName() = " + jm.getName());
            }

            TypedQuery<JMember> paramQuery
                    = em.createQuery("select m from JMember m where m.name = :name", JMember.class)
                    .setParameter("name", "member1678802651403");
            JMember singleResult1 = paramQuery.getSingleResult();

            //매소드 체인
            JMember singleResult2 = em.createQuery("select m from JMember m where m.name = :name", JMember.class)
                    .setParameter("name", "member1678802651403").getSingleResult();

            System.out.println("singleResult2 = " + singleResult2);


            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();

    }
}
