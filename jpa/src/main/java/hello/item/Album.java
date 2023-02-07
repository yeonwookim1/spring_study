package hello.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ALBUM")
public class Album extends PItem {

    private String artist;
}
