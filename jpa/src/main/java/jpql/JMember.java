package jpql;

import javax.persistence.*;

@Entity
public class JMember {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

    private JMemberType type;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public JMemberType getType() {
        return type;
    }

    public void setType(JMemberType type) {
        this.type = type;
    }


    public void changeTeam(JTeam team){
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "JMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
//                ", team=" + team +    //toString시 양방향이 되면 무한루프
                '}';
    }
}
