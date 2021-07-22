package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
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
            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId();

            Member member = em.find(Member.class,memberId);
            Member findMember = order.getMember();


            System.out.println("=======================");
            // commit 시점에 데이터베이스에 쿼리가 날라감
            tx.commit();

        } catch (Exception e ){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
