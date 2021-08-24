package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
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
//            Team team = new Team();
//            team.setName("TeamA");

//            em.persist(team);

//            Member member = new Member();
//            member.setUsername("member1");
            //연관관계 편의 메서드가 양쪽다 있으면 문제가 될수 있기때문에 하나는 지움
//            member.changeTeam(team); //**
            //객체지향스럽지 못한 방식
//            member.setTeamId(team.getId());
//            em.persist(member);

            //연관관계 편의 메서드
//            team.addMember(member);

            //연관관계의 주인이 아니기때문에 조회만 가능
            //객체지향적으로 생각했을때 양쪽 모두 값을 셋팅해주는 것이 맞음
            //** 연관관계 편의 메소드 생성
//            team.getMembers().add(member); //**

            //테스트시 영속성 컨텍스트(1차캐시)를 초기화하여 select 쿼리를 보고 싶을경우 사용
            //이후 db에서 다시 조회
//            em.flush();
//            em.clear();

            //flush, clear를 하지 않으면 team에 member가 없음
            //영속성 컨텍스트에 em.persist(team); 시점의 객체가 1차 캐시로 로딩되어있기 때문에 team에 member 컬렉션이 비어있음
//            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
//            List<Member> members = findTeam.getMembers();

//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//            for (Member m : members){
//                System.out.println("m = " + m.getUsername());
//            }

            //멤버에서 바로 팀을 꺼내서 사용할수 있음
//            Team findTeam = findMember.getTeam();
            //양방향 참조 toSting시 무한루프 발생
//            System.out.println("findTeam = " + findTeam);

            //객체지향스럽지 못한 방식
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class,findTeamId);

//            // 상속관계 매핑 > 조인전략
//            Movie movie = new Movie();
//            movie.setDirector("aaaa");
//            movie.setActor("bbbb");
//            movie.setName("바람과함께사라지다");
//            movie.setPrice(10000);
//
//            em.persist(movie);
//
//            // MappedSuperclass
//            Member member = new Member();
//            member.setUsername("user1");
//            member.setCreatedBy("kim");
//            member.setCreatedDate(LocalDateTime.now());
//
//            em.persist(member);
//
//            //1차캐시 제거
//            em.flush();
//            em.clear();
//
//            Movie findMove = em.find(Movie.class, movie.getId());
//            System.out.println("findMove = " + findMove);

            //프록시
//            Member member = new Member();
//            member.setUsername("hello");
//
//            em.persist(member);
//
//            Member member2 = new Member();
//            member.setUsername("hello2");
//
//            em.persist(member2);
//
//
//            em.flush();
//            em.clear();

            //진짜 엔티티 객체를 줌
//            Member findMember = em.find(Member.class, member.getId());
            //진짜로 객체를 주는것이 아니라 가짜 엔티티 객체를 줌 (id값만 들고있는 껍데기만 반환)
            //getUsername()을 호출하는 시점에 영속성 컨텍스트를 통해서 초기화 요청을 하고 jpa가 db에서 조회하여 실제 Entity 생성
            //생성된 프록시객체(findMember)의 target.getName()으로 실제 객체의 메소드 호출
//            Member findMember = em.getReference(Member.class, member.getId());
//            System.out.println("findMember : " + findMember.getClass()); //class hellojpa.Member$HibernateProxy$N7MF4UtP
//            System.out.println("findMemberId : " + findMember.getId());
//            System.out.println("findMemberUsername : " + findMember.getUsername());
//            System.out.println("findMemberUsername : " + findMember.getUsername());

//            Member m1 = em.find(Member.class, member.getId());
//            System.out.println("m1 : " + m1.getClass());
//            Member m2 = em.getReference(Member.class, member2.getId());

//            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));
//            System.out.println("m1 == m2 : " + (m1 instanceof Member));
//            System.out.println("m1 == m2 : " + (m2 instanceof Member));

            //영속성 컨텍스트에 찾는 엔티티가 이미 있는 경우 em.getReference()를 호출해도 실제 엔티티 반환
//            Member reference = em.getReference(Member.class, member.getId());
//            System.out.println("reference : " + reference.getClass());

            //항상 true를 반환해주어야함
            //m1을 만약 reference로 가져오더라도 같은 프록시를 가져옴 (항상 true되어야 하기 때문)
//            System.out.println("a == a : " + (m1 == reference));

//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember : " + refMember.getClass()); //Proxy 예상
//
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember : " + findMember.getClass()); //Member 예상
//
//            //refMember, findMember 둘다 결과는 Proxy로 나옴
//            //항상 true되어야 하기 때문
//            System.out.println("refMember == findMember : " + (refMember == findMember));

            //
            //**준영속 상태일 때, 프록시를 초기화하면 문제 발생 (could not initialize proxy)**
            //영속성 컨텍스트를 통해서 초기화 요청을 하기 때문
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember : " + refMember.getClass()); //Proxy 예상
//
//            //영속성 컨텍스트를 종료하거나 관리X, clear
//            em.detach(refMember);
////            em.close();
////            em.clear();
//
//            refMember.getUsername();
//            System.out.println("refMember : " + refMember.getUsername());
////            프록시 인스턴스의 초기화 여부 확인
//            System.out.println("isLoaded : " + emf.getPersistenceUnitUtil().isLoaded(refMember));
////            프록시 강제 초기화
//            Hibernate.initialize(refMember);

            //
            // 지연로딩, 즉시로딩 테스트
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);



            em.flush();
            em.clear();

            //sql로 번역이 됨 즉시로딩일 경우  select * from member , select * from team where team_id = '' 두개의 쿼리가 나감
            //즉시로딩이더라도 join fetch 사용하여 주면 실행하는 쿼리에 따라서 조인해서 한방쿼리로 가져옴
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();


//            Member m = em.find(Member.class, member1.getId());
//            // 팀을 프록시 객체로 가져옴
//            System.out.println("m = " + m.getTeam().getClass());
//            System.out.println("=============");
//            // 실제 팀을 사용하는 시점에 초기화
//            System.out.println("teamName = "+m.getTeam().getName());
//            System.out.println("=============");

            tx.commit();

        } catch (Exception e ){
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
