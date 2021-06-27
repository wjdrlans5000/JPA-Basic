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
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//            em.persist(member);

//            Member findmember = em.find(Member.class, 1L);
            //네임 수정 - 별도 저장안해도 업데이트 됨
//            findmember.setName("HelloJPA");

//            System.out.println( "findmember.id = " + findmember.getId());
//            System.out.println( "findmember.name = " + findmember.getName());
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                    //paging 기능
                    .setFirstResult(5)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member: resultList) {
                System.out.println("member.name = " + member.getName());
            }


            tx.commit();

        } catch (Exception e ){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
