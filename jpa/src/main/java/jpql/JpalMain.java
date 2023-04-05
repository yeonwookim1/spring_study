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
            member.setAge(30);
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
//            JMember singleResult1 = paramQuery.getSingleResult();

            //매소드 체인
//            JMember singleResult2 = em.createQuery("select m from JMember m where m.name = :name", JMember.class)
//                    .setParameter("name", "member1678802651403").getSingleResult();
//
//            System.out.println("singleResult2 = " + singleResult2);


            JMember jm = new JMember();
            jm.setName("findJ");
            jm.setAge(10);
            em.persist(jm);

            em.flush();
            em.clear();

            List<JMember> result = em.createQuery("select m from JMember m", JMember.class).getResultList();
            em.createQuery("select o.address from JOrder o", JAddress.class).getResultList();
            JMember findJm = null;

            for(JMember re : result){
                if(re.getName().equals("findJ")){
                    findJm = re;
                    break;
                }
            }
            //영속성 컨텍스트가 관리
            findJm.setAge(22);


            //스칼라 조회시 타입 명시 X
            List rList = em.createQuery("select m.name, m.age from JMember m").getResultList();
            //List<Object[]> rList = ....
            Object o = rList.get(0);
            Object[] resultO = (Object[]) o;
            System.out.println("resultO[0] = " + resultO[0]);
            System.out.println("resultO[1] = " + resultO[1]);

            //DTO 생성 방법
            List<JMemberDTO> dtoList = em.createQuery("select new jpql.JMemberDTO(m.name, m.age) from JMember m", JMemberDTO.class)
                    .getResultList();


            //페이징

            List<JMember> resultList2 = em.createQuery("select m from JMember m order by m.age desc", JMember.class)
                            .setFirstResult(1)
                            .setMaxResults(10)
                            .getResultList();

            for(JMember jm2 : resultList2){
                System.out.println("jm2 = " + jm2);
            }

            //조인
            JTeam jTeam = new JTeam();
            jTeam.setName("teamA");
            em.persist(jTeam);

            JMember joinM = new JMember();
            joinM.setName("member1");
            joinM.setAge(10);
            joinM.setType(JMemberType.ADMIN);

            joinM.changeTeam(jTeam);

            em.persist(joinM);
            em.flush();
            em.clear();

//            List<JMember> resultList3 = em.createQuery(
//                    "select m from JMember m left join m.team t on t.name = 'teamA'", JMember.class).getResultList();
//
            //외부조인 JPQL
            //select m from JMember m left join Team t on t.name = 'teamA'

            String q = "select m.name, 'HELLO', true , m.type from JMember m where m.type = :userType";

            List<Object[]> resultList4 = em.createQuery(q)
                    .setParameter("userType",JMemberType.ADMIN)
                    .getResultList();
            for(Object[] obj : resultList4){
                System.out.println("obj[0] = " + obj[0]);
                System.out.println("obj[1] = " + obj[1]);
                System.out.println("obj[2] = " + obj[2]);
                System.out.println("obj[3] = " + obj[3]);
            }

            String q2 = "select " +
                        "case when m.age <=10 then '학생'" +
                        " when m.age >= 60 then '경로'" +
                        "else '일반요금' end " +
                        "from JMember m";

            //persistance.xml에 path등록
//            String q3 = "select function('group_concat', m.name) from JMember m";
            String q3 = "select group_concat(m.name) from JMember m";

            List<String> result3 = em.createQuery(q3, String.class)
                            .getResultList();
            for(String s1 : result3){
                System.out.println("s1 = " + s1);
            }

            //fetch join
            JTeam jTeam1 = new JTeam();
            jTeam1.setName("팀A");
            em.persist(jTeam1);

            JTeam jTeam2 = new JTeam();
            jTeam2.setName("팀B");
            em.persist(jTeam2);

            JMember jMember = new JMember();
            jMember.setName("messi");
            jMember.changeTeam(jTeam1);
            em.persist(jMember);

            JMember jMember2 = new JMember();
            jMember2.setName("son");
            jMember2.changeTeam(jTeam2);
            em.persist(jMember2);

            em.flush();
            em.clear();

            String joinQ = "select m from JMember m join fetch m.team";

            List<JMember> query2 = em.createQuery(joinQ, JMember.class).getResultList();

            for(JMember mm : query2){
                //System.out.println("m.getName() = " + m.getName() + " "  + m.getTeam().getName());
                if(mm.getTeam() == null){
                    //이전에 삽입했던 member 중에 team이 없는 경우
                    continue;
                }
                System.out.println("m = " + mm.getName() + " mm.getName() " + mm.getTeam().getName());
            }

            String joinQ2 = "select distinct m from JTeam m join fetch m.members";

            List<JTeam> query3 = em.createQuery(joinQ2, JTeam.class).getResultList();
            for(JTeam jt : query3){
                System.out.println("jt.getName() = " + jt.getName());
                for(JMember jtMember : jt.getMembers()){
                    System.out.println("- member = " + jtMember);
                }
            }
            
            //엔티티 직접 사용
            JMember jMember3 = new JMember();
            jMember3.setName("son");
            jMember3.changeTeam(jTeam2);
            em.persist(jMember3);

            String joinQ3 = "select m from JMember m where m = :member";

            List<JMember> query4 = em.createQuery(joinQ3, JMember.class)
                    .setParameter("member", jMember3)
                    .getResultList();

            for(JMember jm2 : query4){
                System.out.println("jt.getName() = " + jm2.getName());
            }

            //namedQuery
            List<JMember> query5 = em.createNamedQuery("JMember.findByName", JMember.class)
                    .setParameter("username", "son")
                    .getResultList();
            for(JMember nameJm : query5){
                System.out.println("namedQuery name = " + nameJm.getName());
            }

            //벌크 연산
            String calQuery = "update JMember m set m.name = 'KYW' where m.name = 'son'";
            //execute시 FLUSH 자동 호출
            int resultCnt = em.createQuery(calQuery).executeUpdate();
            System.out.println("resultCnt = " + resultCnt);
            System.out.println("jMember3.getName() = " + jMember3.getName()); //애플리케이션에서 가지고 있는 값으로 나옴


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
