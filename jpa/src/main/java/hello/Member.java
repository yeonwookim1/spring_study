package hello;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//@Entity
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
//    private Long id;

//@Entity
//@TableGenerator(
//name = "MEMBER_SEQ_GENERATOR",
//table = "MY_SEQUENCES",
//pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE,
//            generator = "MEMBER_SEQ_GENERATOR")
//    private Long id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")  //데이터베이스 칼럼명
    private String username;

    private int age;

    @Enumerated(EnumType.STRING)    //객체에서 enum 타입 사용시
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)   //날짜타입은 temporal 어노테이션 사용
    private Date createdDate;

    private LocalDate testCreateDate;
    private LocalDateTime testCreateDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob                                //큰 범위
    private String description;

    @Transient                          //DB 사용 안하고 메모리에서만 사용
    private int temp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
