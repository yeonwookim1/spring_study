

프로젝션

- SELECT 절에 조회할 대상을 지정

- 엔티티, 임베디드, 스칼라타입(기본 데이터 타입) 가능

- 영속성 컨텍스트가 관리

- ```java
  select m from Member m	//엔티티타입
  select m.team from Member m		//엔티티타입
  select m.address from Member m	//임베디드 타입
  select m.username, m.age from Member m		//스칼라타입(기본 데이터 타입)

  em.createQuery("select m from JMember m", JMember.class)
  .getResultList();	//엔티티조회
  em.createQuery("select o.address from JOrder o", JAddress.class)
  .getResultList();	//임베디드 조회
  List resultList = em.createQuery("select m.name, m.age from JMember m")
  .getResultList();	//스칼라 조회
  ```

- 스칼라 조회시 반환 타입이 2개 이상일 경우 Object로 반환 되기 때문에 타입 캐스팅이 필요

- ```java
  //캐스팅 필요, 스칼라 조회시 타입 명시 X
  List rList = em.createQuery("select m.name, m.age from JMember m").getResultList();
  Object o = rList.get(0);
  Object[] resultO = (Object[]) o;

  //제네릭을 명시
  List<Object[]> rList = ....

  //DTO 생성 방법
  List<JMemberDTO> dtoList = em.createQuery("select new jpql.JMemberDTO(m.name, m.age) from JMember m", JMemberDTO.class)
  .getResultList();
  ```




페이징 API

- 매소드를 사용하여 제한(limit, offet)

- ```java
  List<JMember> resultList2 = em.createQuery("select m from JMember m order by m.age desc", JMember.class)
  .setFirstResult(1)
  .setMaxResults(10)
  .getResultList();
  ```




조인

- 내부 조인 : select m from Member m [INNER] JOIN m.team t

- 외부 조인 : select m from Member m Left [OUTER] JOIN m.team t

- 세타 조인(관계가 없는) : select count(m) from Member m, Team t where m.username = t.name

- ON절을 활용한 조인

  - 조인 대상 필터링

  - ```
    ex : 회원과 팀을 조인하면서 팀 이름이 A인 팀만 조인
    JPQL
    select m, t from Member m left join m.team t on t.name = 'A'

    => 
    SQL
    select m.*, t.* from
    Member m left join Team t on m.Team_id=t.id and t.name = 'A'
    ```

    ​

  - 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1 부터)

  - ```
    ex : 회원의 이름과 팀의 이름이 같은 대상 외부 조인
    JPQL
    select m,t from Member m left join Team t on m.username = t.name

    =>
    SQL
    select m.*, t.* from
    Member m left join Team t on m.username = t.name
    ```




서브쿼리

- 서브로 쿼리를 만들어서 사용
- ALL / ANY / IN   / exists 사용
- JPA는 where, having 절에서만 서브 쿼리사용 가능
- select절도 가능(하이버네이트에서 지원)
- from절의 서브쿼리 불가(인라인) -> join 절로 풀어서 해결, 네이티브 쿼리로 해결