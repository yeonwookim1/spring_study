package hello.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AL")
public class Album extends PItem {

    private String artist;
}
