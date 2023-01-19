package hello;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<User> users = new ArrayList<>();

//    @OneToMany(mappedBy = "team")   //ManyToOne의 반대 방향, 참조하려고 사용, mappedBy = '변수명'
//    private List<User> users = new ArrayList<>();

//    public void addUser(User user){
//        user.setTeam(this); //주인에 먼저 셋팅
//        users.add(user);
//    }

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
