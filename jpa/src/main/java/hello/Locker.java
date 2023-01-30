package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

    //일대일
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")  //일대일 mappedby 에서도 변수명을 넣어준다.
    private User user;

}
