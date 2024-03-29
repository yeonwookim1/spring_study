package hello;

import hello.item.Movie;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //삽입
//            Member member = new Member(12L, "kyw");

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

            //flush
//            Member fMember = new Member(200L,"FLUSH");
//            em.persist(fMember);
//            em.flush();
//            System.out.println("flush");

            //준영속
//            Member member1 = em.find(Member.class,201L);
//            member1.setName("detach2");
//            em.detach(member1);

//            Member member = new Member();
//            member.setId(new Random().nextLong());
//            member.setName("kyw");
//            em.persist(member);

            //IDENTITY 전략
//            Member member = new Member();
//            member.setUsername("A");
//            member.setRoleType(RoleType.USER);
//
//            System.out.println("====== GenerationType.IDENTITY 일때는 ID 값을 위해 persisit 때 insert 쿼리 =========");
//            em.persist(member);
//            System.out.println("===============");
//            System.out.println("member.getId() = " + member.getId());
//
//
//            //table 전략
//            Member member1 = new Member();
//            member.setUsername("A");
//
//            Member member2 = new Member();
//            member.setUsername("B");
//            member.setRoleType(RoleType.USER);
//
//            em.persist(member1);    //1, 51
//            em.persist(member2);    //MEM

//            Member member1 = em.find(Member.class, 1L);
//            member1.setUsername("AAA");
//            member1.setRoleType(RoleType.ADMIN);

//             //다대일 관계
//            Team team = new Team();
//            team.setName("TeamB");
//            em.persist(team);
//
//            User user = new User();
//            user.setUserName("user1");
//            em.persist(user);
//
//            //team.getUsers().add(user);  //양방향 매핑시 양쪽 다 셋팅 해주는게 좋음 -> 편의 매소드 사용
//
//            team.addUser(user);
//            //user나 team 한쪽에서만 편의매서드를 사용
//
//            //flush, clear 안해주면 1차캐시만 참고하기 때문에 조회가 되지 않음, 양쪽 다 셋팅 하는 것
//            em.flush();
//            em.clear();
//
//
//            User findUser = em.find(User.class, user.getId());
//            List<User> userList = findUser.getTeam().getUsers();
//
//            for(User u : userList){
//                System.out.println("u.name = " + u.getUserName());
//            }


            //일대다 관계
//            User user = new User();
//            user.setUserName("test");
//            user.setCreateBy("kyw");
//            user.setCreateDate(LocalDateTime.now());
//
//            em.persist(user);
//
//            Team team = new Team();
//            team.setName("manU");
//            team.setCreateBy("kyw");
//            team.setCreateDate(LocalDateTime.now());
//            team.getUsers().add(user);
//
//            em.persist(team);
//
//            System.out.println("========상속 매핑=======");
//
//            Movie movie = new Movie();
//            movie.setDirector("aaa");
//            movie.setActor("sss");
//            movie.setName("pocketmon");
//            movie.setPrice(10000);
//
//            em.persist(movie);
////            em.flush();
////            em.clear();
//
//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println(findMovie);


            System.out.println("=============================================");
            //proxy
            User user1 = new User();
            user1.setUserName("kk1");
            em.persist(user1);

            User user2 = new User();
            user1.setUserName("kk2");
            em.persist(user2);

            em.flush();
            em.clear();

            User findUser1 = em.find(User.class, user1.getId());
            User findUser2 = em.getReference(User.class, user2.getId());

//            User refUser = em.getReference(User.class, user1.getId());
//            System.out.println("refUser.getClass(); = " + refUser.getClass()); //class hello.User$HibernateProxy$EfKDavGx
//            System.out.println(refUser.getUserName());

            System.out.println("fineUser1 == findUser2 = " + (findUser1.getClass() == findUser2.getClass())); //false
            System.out.println("instanceof = " + (findUser1 instanceof User)); //true

            System.out.println(findUser1.getClass());
            User refUser = em.getReference(User.class, user1.getId());
            System.out.println("refUser = " + refUser.getClass());  //영속성콘텍스트(1차캐시)에 있으면 실제 엔티티가 조회

            em.flush();
            em.clear();

            User ref = em.getReference(User.class, user1.getId());
            System.out.println("ref.getClass()" + ref.getClass());
            User find = em.find(User.class, user1.getId());
            System.out.println("find.getClass()" + find.getClass()); // -> 프록시로 조회된다. 동일성 보장
            System.out.println("ref == find = " + (ref == find));

            em.flush();
            em.clear();
            

            //준영속인 경우에 참조하지 못하여 예외 발생
//            User deRef = em.getReference(User.class, user1.getId());
//            em.detach(deRef);   //em.close(), clear()도 동일하게 예외 발생
//            System.out.println("deRef.getUserName() = " + deRef.getUserName());

            User infoProxy = em.getReference(User.class, user1.getId());

            //프록시 초기화 여부 확인
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(infoProxy) = "
                    + emf.getPersistenceUnitUtil().isLoaded(infoProxy));

            //프록시 클래스 이름 확인
            System.out.println("infoProxy.getClass() = " + infoProxy.getClass().getName());

            //프록시 강제 초기화
            Hibernate.initialize(infoProxy);

            System.out.println("emf.getPersistenceUnitUtil().isLoaded(infoProxy) = "
                    + emf.getPersistenceUnitUtil().isLoaded(infoProxy));


            User user = new User();
            user.setUserName("lazyUser");
            Team lazyTeam = new Team();
            em.persist(lazyTeam);
            user.setTeam(lazyTeam);
            em.persist(user);
            em.flush();
            em.clear();

            User lazyUser = em.find(User.class, user.getId());
            System.out.println("lazyUser.getTeam().getClass() = " + lazyUser.getTeam().getClass());

            em.createQuery("select m from User m join fetch m.team", User.class)
                            .getResultList();



            //영속성 전이 CASCADE

            Parent parent = new Parent();

            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            //cascade 추가하면 child는 persist를 안해도 된다.
//            em.persist(child1);
//            em.persist(child2);

            //고아객체
            em.flush();
            em.clear();
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            
            //임베디드 타입
            User user3 = new User();
            user3.setUserName("hello");
            user3.setHomeAddress(new Address("seoul", "sangam", "54"));

            em.persist(user3);


            //값타입 변경 및 불변객체

            Address address = new Address("seoul", "sangamdong", "52");

            User user4 = new User();
            user4.setUserName("user4");
            user4.setHomeAddress(address);
            em.persist(user4);

            User user5 = new User();
            user5.setUserName("user4");
            user5.setHomeAddress(address);
            em.persist(user5);

            //user4, user5 다 바뀌게 된다.
//            user4.getHomeAddress().setCity("usa");

            //새로운 객체를 할당하여 변경해야 함
            Address newAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode());
            user4.setHomeAddress(newAddress);
            //값타입을 불변객체로 선언
//            user4.getHomeAddress().setCity("korea");

            //값타입 컬렉션
            User user6 = new User();
            user6.setUserName("test1");
            user6.setHomeAddress(new Address("city1", "steet1", "zipcode1"));

            user6.getFavoriteFoods().add("치킨");

            user6.getAddressHistory().add(new AddressEntity("oldCity", "oldStreet1", "oldZipcode1"));
            user6.getAddressHistory().add(new AddressEntity("old1", "oldStreet1", "oldZipcode1"));

            em.persist(user6);
            em.flush();
            em.clear();

            User findUser6 = em.find(User.class, user6.getId());

            //set을 해줄때는 새로운 객체로 교체를 해준다.
            findUser6.getAddressHistory().add(new AddressEntity("newCity", "oldStreet1", "oldZipcode1"));

            //치킨->한식
            findUser6.getFavoriteFoods().remove("치킨");
            findUser6.getFavoriteFoods().add("한식");
            System.out.println("findUser6.getId() = " + findUser6.getId());

            findUser6.getAddressHistory().remove(new AddressEntity("old1", "oldStreet1", "oldZipcode1"));
            findUser6.getAddressHistory().add(new AddressEntity("newCity1", "oldStreet1", "oldZipcode1"));

            em.persist(findUser6);


            System.out.println("================JPQL================");

            List<User> result = em.createQuery(
                    "select m From User m ", User.class).getResultList();

            for(User itUser : result){
                System.out.println("itUser.getUsername() = " + itUser.getUserName());
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);

            Root<User> m = query.from(User.class);
            //CriteriaQuery<User> cq = query.select(m).where(cb.equal(m.get("USERNAME"),  "kim"))
            CriteriaQuery<User> cq = query.select(m);
            String username = "test";
            if(username != null){
                cq = cq.where(cb.equal(m.get("USERNAME"),  "kim"));

            }
            List<User> resultList = em.createQuery(cq).getResultList();

            //native query
            em.createNativeQuery("selecft * from user");

            System.out.println("===============");
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void printUserAndTeam(User user){
        System.out.println(user.getUserName());

        Team team = user.getTeam();
        System.out.println(team.getName());
    }

    private static void printUser(User user){
        System.out.println(user.getUserName());
    }
}
