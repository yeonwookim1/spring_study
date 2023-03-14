package jpql;

import javax.persistence.*;

@Entity
public class JMember {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private JTeam team;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
