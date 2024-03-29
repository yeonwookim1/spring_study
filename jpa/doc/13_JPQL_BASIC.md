

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




타입

- 대문자 '' 사용

- 숫자 : 10L (long), 10D(double), 10F(Float)

- boolean : true, false

- ENUM : jpql.JMemberType.ADMIN (패키지명 포함)

- ```
  enum은 바인드변수로 해주는게 깔끔

  String q = "select m.name, 'HELLO', true , m.type from JMember m where m.type = :userType";

  List<Object[]> resultList4 = em.createQuery(q)
  .setParameter("userType",JMemberType.ADMIN)
  .getResultList();
  ```

  ​

조건식

- coalesce : 하나씩 조회해서 null이 아니면 반환
- nullif : 두값이 같으면 null, 다르면 첫번째 값



JPQL 함수

- 기본 함수 : COCNAT(||), SUBSTRING, TRIM, LOWER, UPPER 등

- 사용자 정의 함수 : 사용전 방언에 추가해야 한다.

  - 사용하는 DB 방언을 상속 받고 사용자 정의 함수를 사용(클래스 및 persistance에 등록)

- ```java
  String q3 = "select function('group_concat', m.name) from JMember m";
  String q3 = "select group_concat(m.name) from JMember m";
  ```

  ​

경로표현식

- 점을 찍어 객체 그래프를 탐색하는 것
- 상태필드 : 단순히 값을 저장하기 위한 필드(ex : m.name), 경로 탐색의 끝(탐색X)
- 연관필드 : 연관관계를 위한 필드
  - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티,  묵시적 내부 조인 발생(탐색O)
  - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션,  묵시적 내부 조인 발생(탐색X)
  - FROM 절에서 조인을 하여 alias를 사용하면 컬렉션 값에 대한 필드 값을 가져올 수 있음
- 실무에서 명시적 조인 사용, 묵시적 조인은 파악하기 어려움



Fetch Join

- SQL식이 아니라 JPQL에서 성능최적화를 위해 제공하는 기능

- 연관된 엔티티나 컬렉션을 SQL 한번에 함께 조회하는 기능

- ```sql
  //jpql
  select m from JMember m join fetch m.team

  //sql
  select m.*, t.* from member m inner join team t on m.team_id = t.id
  ```

- join을 사용하면  n + 1 문제를 해결할 수 있다.

- **지연로딩보다 fetch join이 우선

- join으로 인한 중복 제거 : distinct

  - SQL에 distinct(같은 식별자를 가진 엔티티를 제거)
  - 어플리케이션에서 중복제거 로직 추가

- fetch join vs join

  - join은 연관된 엔티티를 조회하지 않음
  - fetch join을 사용할 때는 연관된 엔티티도 함께 조회, 객체 그래프를 한번에 조회하는 개념

- 페치 조인 대상에는 별칭을 줄 수 없다. -> 데이터 정합성

- 둘 이상의 컬렉션은 페치 조인을 할 수 없다. -> 데이터 정합성

- 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResult)를 사용할 수 없다.

  - 일대일, 다대일은 가능하지만 데이터가 늘어나는 경우(일대다)  불가

- 정리

  - 연관된 엔티티를 한번에 조회(성능 최적화)
  - 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함(@OneToMany(FetchType=LAZY))
  - 실무에서 글로벌 로딩 전략은 모두 지연 로딩
  - 최적화가 필요한 곳은 페치 조인 사용
  - 객체 그래프를 유지할 때 사용하면 효과적
  - 여러 테이블을 조인해서 전혀 다른 결과를 내야하면 페치 조인보다는 일반 조인을 사용하여 DTO로 반환하는 것이 효과적



- 다형성 쿼리

  - 조회 대상을 특정 자식으로 한정

  - ```
    //JPQL
    select i from Item i where type(i) IN (Book, Item)

    //SQL
    select i from i where i.DTYPE in ('B', 'I')
    ```

  - TREAT (다운 캐스팅 느낌)

  - ```
    //JPQL
    select i from Item i where threat(i as Book).auther = 'kim'

    //SQL
    select i from i where i.DTYPE = 'B' and i.auther = 'kim'
    ```



- 엔티티 직접 사용

  - JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용

  - ```
    //JPQL
    "select m from JMember m where m = :member";
    "select m from JMember m where m.id = :member_id";

    //위의 두 쿼리 둘 다 동일한 SQL이 실행된다.

    //SQL
    select m from JMember m where m.id = member_id;
    ```

    ​

- Named 쿼리 - 어노테이션

  - 쿼리에 이름을 선언해서 사용

  - 정적 쿼리, 어노테이션, XML에 정의

  - 애플리케이션 로딩 시점에 초기화 후 재사용(캐시에 담아둠)

  - 로딩 시점에 검증

  - ```java
    //entity에 선언
    @Entity
    @NamedQuery(
            name = "JMember.findByName",
            query = "select m from JMember m where m.name = :username"
    )
    ...
      
    em.createNamedQuery("JMember.findByName", JMember.class)
      				.setParameter("username", "son")
      				.getResultList();
    ```

  - XML에 정의 하여 사용할 수도 있다.

  - XML이 항상 우선권을 가진다.

    ​



- 벌크연산

  - UPDATE문과 delete문을 위해 사용(ex : 조건에 따른 100만건을 update 하기 위해)

  - 쿼리 한 번으로 여러 테이블의 로우 변경(엔티티) 

  - executeUpdate()의 결과는 영향 받은 엔티티의 수를 반환

  - INSERT는 하이버네이트에서만 지원

  - ```java
    jMember3.setName("son");
    ...
    String calQuery = "update JMember m set m.name = 'KYW' where m.name = 'son'";
    //execute시 FLUSH 자동 호출
    int resultCnt = em.createQuery(calQuery).executeUpdate();

    //애플리케이션에서 가지고 있는 값으로 나옴
    System.out.println("jMember3.getName() = " + jMember3.getName()); 

    //따라서 초기화후 다시 값을 가져와야함
    em.clear();
    ```

  - 벌크 연산은 영속성 컨텍스트를 무시하고 직접 쿼리

    1. 벌크 연산을 먼저 실행


    2. 벌크 연산 수행 후 연속성 콘텍스트 초기화(em.clear())