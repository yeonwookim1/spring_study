package hello;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String userName;

    //다대다
//    @ManyToMany
//    @JoinTable(name = "user_product")
//    private List<Product> products  = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<userProduct> userProducts = new ArrayList<>();


    //일대일
    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

//일대다
//    @Column(name = "team_id")
//    private Long teamId;

    //다대일
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        List<String> list = new ArrayList<>();
        String[] arr = list.stream().toArray(String[]::new);
        list =Arrays.stream(arr).collect(Collectors.toList());
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
