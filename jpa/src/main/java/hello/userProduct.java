package hello;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class userProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;
    private int price;

    private LocalDateTime orderDateTime;


}
