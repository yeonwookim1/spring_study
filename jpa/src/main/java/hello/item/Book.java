package hello.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BK")
public class Book extends PItem {

    private String author;
    private String isbn;
}
