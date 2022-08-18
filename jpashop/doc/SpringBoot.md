1. 스프링 부트
   - 스프링을 간결하고 쉽게 사용할 수 있도록 지원


2. JPA
   - 강력한 자바 ORM 표준기술





3. 프로젝트 셋팅
   - lombok 사용시 plugin에서 lombok 다운
   - Compiler -> Annotation Processros에서 Enable annotation processing 체크



4. JPA

   - 엔티티 :  테이블과의 매핑

   - @Id : 기본키

   - ```java
     @Entity
     public class Member {

      	@Id
         @GeneratedValue
         private Long id;
     ...

     }
     ```

     ​