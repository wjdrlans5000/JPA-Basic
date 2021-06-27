# JPA-Basic
## JPA프로그래밍-기본
### JPA 소개
- Java Persistence API
- 자바 진영의 ORM 기술 표준

- Object-relational mapping (객체관계매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재
> JPA는 애플리케이션과 JDBC 사이에서 동작

- JPA는 표준명세
    - 인터페이스의 모음임
    - JPA 2.1 표준 명세를 구현한 3가지 구현체
    - 하이버네이트, EclipseLink, DataNucleus

- JPA를 사용하는 이유
    - SQL중심적인 개발에서 객체중심으로 개발
    - 생산성 증가
    - 유지보수 쉬움
    - 패러다임의 불일치 해결
    - 성능
    - 데이터 접근 추상화와 벤더 독립성
    - 표준
    
- JPA와 CRUD
    - 저장 : jpa.persist(member)
    - 조회 : Member member = jpa.find(memberId);
    - 수정 : member.setName("변경할이름") -> 바로 db업데이트 됨.
    - 삭제 : jpa.remove(member)
    
- JPA의 성능 최적화 기능
    - 1차 캐시와 동일성 보장
        - 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상
    - 트랜잭션을 지원하는 쓰기 지연
        - 트랜잭션을 커밋할때까지 INSERT SQL을 모음
        - JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송
    - 지연로딩
        - 객체가 실제 사용될때 로딩
    - 즉시로딩
        - JOIN SQL로 한번에 연관된 객체까지 미리 조회
        
- JPA 구현체로 하이버네이트를 사용하기 위한 핵심 라이브러리
    - hibernate-core
        - 하이버네이트 라이브러리
    - hibernate-entityManager
        - 하이버네이트가 JPA 구현체로 동작하도록 JPA 표준을 구현한 라이브러리
    - hibernate-jpa-2.1api
        - JPA 2.1 표준을 모아둔 라이브러리
    - hibernate-entityManager 의존성을 추가할경우 나머지 라이브러리도 함께 따라온다.

- JPA 설정 - persistence.xml
    - 기본 경로는 classpath:META-INF/persistnence.xml
    
- JPA의 특징
    - 데이터베이스 방언
        - JPA는 특정 데이터베이스에 종속적이지 않기 때문에 다른 데이터베이스로 손쉽게 교체가 가능하다.
        - 데이터베이스에는 각 벤더별로 다른 점이 조금씩 존재하는데 이것을 데이터베이스 방언이라고 한다.

> 애플리케이션 개발자가 특정 데이터베이스에 종속적인 기능을 사용하게되면, 나중에 교체가 어려워지게 된다.
  하지만 하이버네이트는 이런 문제를 해결하기 위해 방언 클래스를 제공한다.
  개발자는 JPA가 제공하는 표준 문법에 맞춰 JPA를 사용하게 되면, 데이터베이스가 변경되어도 코드 변경없이 데이터베이스 방언만 교체해 주면된다.

- JPA 구동 방식
    - Persistence 클래스는 persistence.xml 을 참조한다.
        - 설정정보 조회
        - EntityManagerFactory 생성
        - EntityManager를 생성하여 사용

- 멤버 테이블 생성
```sql
CREATE TABLE MEMBER (
    ID VARCHAR(255) NOT NULL,   -- 아이디 (기본키)
    NAME VARCHAR(255),          -- 이름
    PRIMARY KEY (ID)
)
```

- JPA를 사용하려면 persistence.xml 의 설정 정보를 사용해 엔티티 매니저 팩토리를 생성 해야 한다.
- 엔티티 매니저 팩토리를 이용하여 엔티티 매니저를 생성한다.
- 엔티티 매니저를 이용해 CRUD를 할 수 있으며 내부적으로 데이터베이스 커넥션을 유지하며 데이터베이스와 통신한다.
- 개발자는 엔티티 매니저를 가상의 데이터베이스로 생각 할 수 있다.
- 엔티티 매니저는 Thread-Safe 하지 않기 때문에 스레드간에 공유해서는 안된다.
- JPA를 사용하면 커맨드성 행위 (Insert, Update, Delete)는 반드시 트랜잭션 내에서 이루어 져야한다.
  그렇지 않을경우 Exception이 발생한다.

- @Entity
    - 이 클래스를 테이블과 매핑하기 위해 JPA에게 정보를 제공한다.
    - 이것을 Entity클래스 라고 한다.
- @Table
    - 엔티티 클래스와 매핑할 테이블의 정보를 알려준다.
    - 생략이 가능하며, 기본전략은 클래스명과 동일한 이름으로 매핑한다.
- @Id
    - 엔티티 클래스의 필드를 테이블의 기본키(PK)에 매핑한다.
- @Column
    - 엔티티 클래스의 필드를 컬럼에 매핑한다.
    - 생략이 가능하며, 기본전략은 필드명과 동일한 이름으로 매핑한다.
    
- 엔티티를 수정할때 em.update() 같은 메소드를 호출해야할것 같지만, 단순히 값만 변경해도 데이터가 수정된다.
  JPA는 엔티티가 변경되었는지 추적가능한 변경감지 기능 (Dirty Checking) 을 제공한다.
  
- 단건조회
- 엔티티매니저의 find 메소드는 조회할 엔티티 타입과 @Id 애노테이션으로 매핑한 식별자의 값으로 엔티티 하나를 조회하는 메소드이다.
```java
  Member findmember = em.find(Member.class, 1L);
```

- JPQL 소개
    - JPA를 사용하면 애플리케이션 개발자는 엔티티 객체를 중심으로 개발하고, 데이터베이스에 대한 처리는 JPA에게 맡겨야한다.
    - JPA 엔티티 객체 중심으로 개발하므로, 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야한다.
    - 하지만 테이블이 아닌 엔티티 객체를 대상으로 검색하려면 데이터베이스의 모든 데이터를 애플리케이션을 불러와 검색하는것은 불가능하다. 
    - 애플리케이션이 필요한 데이터만 불러오려면 결국 검색조건이 포함된 SQL 이 필요하다.
    - 데이터베이스를 대상으로 쿼리를 하게되면 특정 벤더에 종속되어 버린다.
    - 이를 해결하기 위해 객체를 대상으로 할 수 있는 JPQL을 지원한다.
    - GROUP BY HAVING 등 모두 지원한다.
```java
    List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
            //paging 기능
            .setFirstResult(5)
            .setMaxResults(10)
            .getResultList();
```