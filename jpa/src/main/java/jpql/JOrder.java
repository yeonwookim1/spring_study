package jpql;

import javax.persistence.*;

@Entity
@Table(name = "JORDERS")
public class JOrder {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private JAddress address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private JProduct product;

}
