1. JPQL
   - 객체지향 쿼리 언어, 테이블을 대상으로 쿼리가 아니라 엔티티객체를 대상으로 쿼리



2. 문법

   - ```sql
     select m from JMember as m where m.age > 20
     ```

   - 엔티티와 속성은 대소문자 구분(Member, age)

   - JPQL 키워드는 대소문자 구분 X

   - 테이블 이름이 아닌 엔티티 이름 사용(클래스 명)

   - 별칭은 필수(as는 생략 가능)




2-1 반환

   - TypeQuery : 반환 타입이 명확, Query : 반환 타입이 명확하지 않을 때

   - ```sql
     //반환타입이 명확
     TypedQuery<JMember> query = em.createQuery("select m from JMember m", JMember.class);
     TypedQuery<String> nameQuery = em.createQuery("select m.username from JMember m", String.class);

     //반환타입이 불명확(여러개일 때)
     Query query1 = em.createQuery("select m.username, m.age from JMember m");
     ```




2-2 결과조회


   - getResultList() 매서드를 통해서 List형태로 반환
     - 결과가 하나 이상일 떄, 리스트로 반환
   - getSingleResult() 결과가 정확히 하나, 단일 객체 반환





2-3  파라미터 바인딩

- getResultList() 매서드를 통해서 List형태로 반환

- 쿼리에 :변수명으로 선언하여 setParameter 매서드를 통해 추가

- ```sql
  TypedQuery<JMember> paramQuery = em.createQuery("select m from JMember m where m.name = :name", JMember.class).setParameter("name", "member1678802651403");
  JMember singleResult = paramQuery.getSingleResult();
  ```

- 매서드체인으로 표현 가능

- 위치기준으로 사용할 수 있으나 권장하지 않음 -> 데이터 추가시 수정사항 많음