package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //양방향 연관관계 매핑
    //mappedBy = "team" > team을 연관관계의 주인으로 지정하여 team에 의해 관리됨
    //members로는 조회만 가능
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    //연관관계 편의 메서드
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

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

    // 양쪽으로 team, members toString 무한 호출해버림
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
