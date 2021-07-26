package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        // Persistence클래스가 EntityManagerFactory를 생성할때 unitName을 인자로 받는다.
        // Factory를 생성하는 순간 데이터베이스와의 연결도 완료된다.
        // 애플리케이션 로딩 시점에 딱 하나만 생성해야한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //자바 컬렉션이라고 생각하면됨 객체를 저장해주는 것
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/*            Member member = new Member();
//            member.setId("id_a");
            member.setUsername("A");
            member.setRoleType(RoleType.USER);

            Member member2 = new Member();
//            member.setId("id_a");
            member2.setUsername("B");
            member2.setRoleType(RoleType.USER);

            Member member3= new Member();
//            member.setId("id_a");
            member3.setUsername("C");
            member3.setRoleType(RoleType.USER);
            System.out.println("=======================");

            //IDENTITY 전략의 경우 persist 시점에 insert 쿼리가 날라감. 이 시점에 id값을 알수 있음
            em.persist(member); //SEQUENCE 전략 1,51
            em.persist(member2); //MEM
            em.persist(member3); //MEM

            System.out.println("member1 : " + member.getId());
            System.out.println("member2 : " + member2.getId());
            System.out.println("member3 : " + member3.getId());

            System.out.println("=======================");*/
            // commit 시점에 데이터베이스에 쿼리가 날라감

            //연관관계 매핑 기초
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("member1");
            //jpa가 알아서 팀에서 pk값을 꺼내서 외래키로 조인
            member.setTeam(team);
            //객체지향스럽지 못한 방식
//            member.setTeamId(team.getId());
            em.persist(member);

            //테스트시 영속성 컨텍스트(1차캐시)를 초기화하여 select 쿼리를 보고 싶을경우 사용
//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            //멤버에서 바로 팀을 꺼내서 사용할수 있음
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            //객체지향스럽지 못한 방식
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class,findTeamId);


            tx.commit();

        } catch (Exception e ){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
