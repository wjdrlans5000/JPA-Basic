package jpabook.jpashop;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
//            Order order = em.find(Order.class, 1L);
//            Long memberId = order.getMemberId();
//
//            Member member = em.find(Member.class,memberId);
//            Member findMember = order.getMember();

            //꼭 양방향 연관관계가 아니어도 애플리케이션 개발하는데 큰 문제는 없음
            //최대한 단방향으로 하는것을 추천
//            Order order = new Order();
//            em.persist(order);
            //양방향 연관관계를 만드는 이유는 개발상의 편의나 기타 다른 필요한 이유가 있을때 사용
//            order.addOrderItem(new OrderItem());

//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            em.persist(orderItem);

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);


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
