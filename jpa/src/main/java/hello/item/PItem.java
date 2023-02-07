package hello.item;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//조인전략(JOINED), 하나의 테이블 전략(SINGLE_TABLE), 각각 테이블 전략(TABLE_PER_CLASS) 사용 가능
@DiscriminatorColumn
public abstract class PItem {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
