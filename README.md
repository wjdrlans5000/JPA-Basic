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

- 