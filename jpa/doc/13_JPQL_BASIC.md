

프로젝션

- SELECT 절에 조회할 대상을 지정

- 엔티티, 임베디드, 스칼라타입(기본 데이터 타입) 가능

- 영속성 컨텍스트가 관리

- ```
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

- ```
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

