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

- 영속성 컨텍스트
    - 영속성 컨텍스트 (Persistence Context) 는 엔티티를 영구 저장하는 환경 이다.
    - 엔티티 매니저를 통해 엔티티를 관리하면 영속성 컨텍스트를 통해 관리하게 된다.

- 영속성 컨텍스트를 통해 관리되는 엔티티는 생명주기 (Life Cycle) 가 존재하는데, 아래의 4가지로 구분된다.
    - 비영속 (new/transient) 상태 : 비영속 상태는 transient 상태라고 하며 영속성 컨텍스트와는 전혀 관련이 없는 상태를 말한다.
    - 영속 (managed) 상태 : 영속 상태는 managed 상태라고 하며 영속성 컨텍스트가 관리하는 상태를 말한다.
    ```java
      Member member = new Member("member1", "회원1");
      em.persist(member); // 영속화
    ```
    - 준영속 (detached) 상태 : 준영속 상태는 detached 상태라고 하며 영속성 컨텍스트가 관리중이 었다가 관리를 하지 않는 상태를 말한다.
    ```java
      Member member = new Member("member1", "회원1");
      em.persist(member); // 영속화
      em.detach(member); // 준영속화
    ```
    - 삭제 (removed) 상태
    
- 영속 상태의 엔티티를 준 영속 상태로 만드는 방법
    - em.defach(entity);
        - 특정 엔티티를 준영속화 한다.
    - em.clear();
        - 영속성 컨텍스트를 완전히 초기화 한다
    - em.close();
        - 영속성 컨텍스트를 종료한다.
        
- 영속성 컨텍스트는 엔티티를 구분할때 엔티티의 식별자 값으로 구분하기 때문에 영속 상태의 엔티티는 반드시 식별자 값이 존재해야 한다.
- JPA는 일반적으로 트랜잭션을 커밋하는 순간 영속성 컨텍스트에 존재하는 엔티티를 데이터베이스에 반영하는데 이것을 플러시 (flush) 라고 한다.

- 영속성 컨텍스트를 사용할때의 장점
    - 1차 캐시
        - 영속성 컨텍스트는 내부에 캐시를 가지고 있는데 이것을 1차 캐시 라고 한다. 영속 성태의 엔티티는 모두 이곳에 저장된다.
        - 영속성 컨텍스트에서 엔티티를 조회할 경우 먼저 1차 캐시에서 식별자 값을 이용해 엔티티 조회를 시도한다.
          영속성 컨텍스트에 엔티티가 존재하지 않는다면, 데이터베이스에서 새롭게 조회한 뒤 영속성 컨텍스트에 저장을 하고
          해당 엔티티를 반환한다.
        - 하나의 트랜잭션 안에서 1차캐시를 가짐
    - 엔티티의 동일성 보장
        - 같은 트랜잭션 내에서 영속성 컨텍스트는 동일 식별자에 대한 엔티티에 대해 동일성을 보장한다.
          같은 트랜잭션 내에서는 수차례 조회 하더라도 1차 캐시에 존재하는 같은 인스턴스를 반환한다.
    - 쓰기지연
        - 엔티티 매니저는 트랜잭션을 커밋하기 직전까지 내부 쿼리 저장소에 SQL 을 모아뒀다가 트랜잭션을 commit 하는 시점에 모아둔 쿼리를
          한번에 데이터베이스로 보내게 되는데 이를 트랜잭션을 지원하는 쓰기지연 (write-behind) 라고 한다.
    - 변경 감지(더티체킹)
        - JPA로 엔티티를 수정할 때는 엔티티를 조회해서 데이터만 변경하면 된다.
        - 트랜잭션 커밋 직전에 스냅샷과 엔티티의 변경점을 비교해 데이터베이스에 자동으로 반영을 해주는 기능을 지원하는데 이런 기능을 변경 감지 (dirty-checking) 이라고 한다.
        ```java
          Member member = em.find(Member.class, 150L);
          member.setName("ZZZZ");
        ```
      > 변경 감지 기능은 영속성 컨텍스트가 관리하는 영속상태의 엔티티에만 적용된다.
        영속성 컨텍스트가 관리하지 않는 엔티티는 값이 변경되어도 데이터베이스에 관리되지 않는다.
- JPA의 데이터 수정 기본 전략 은 엔티티의 모든 필드를 수정한다.
  데이터 전송량이 증가하지만, 쿼리를 재사용할 수 있기 때문에 성능상 이점을 가져오게 된다.
  또한 일반적으로 컬럼이 30개가 넘어가지 않는 이상, 모든 컬럼을 수정하는 쿼리를 재사용하는 것이 성능한 이득이다.
  
- 플러시
    - 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
    - 플러시 발생과정
        - 변경감지
        - 수정된 엔티티 쓰기지연 sql 저장소에 등록
        - 쓰기지연 sql 저장소의 쿼리를 데이터베이스에 전송(등록,수정,삭제쿼리)
    - 영속성 컨텍스트를 플러시하는 방법
        - em.flush() 직접호출
        ```java
          Member member = new Member(200L, :"member200");
          em.persist(member);
          em.flush();
        ```
        - 트랜잭션 커밋 - 플러시 자동 호출
            - 변경 내용을 SQL 로 전달하지 않고 트랜잭션만 커밋하면 어떤 데이터도 데이터베이스에 반영되지 않는다.
        - jpql 쿼리 실행 - 플러시 자동 호출
            - JPQL 실행 이전에 엔티티 매니저를 통해 엔티티를 영속화 했다면, 데이터베이스에는 해당 엔티티에 대한 정보가 없다.
            - JPQL 은 SQL로 변환되어 데이터베이스 에서 엔티티를 조회하기 때문에 영속화된 엔티티를 조회할 수 없다.
    - 플러시가 일어나도 1차캐시는 지워지지않음
    
- 준영속 상태
    - 영속 -> 준영속
    - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리
    - 영속성 컨텍스트가 제공하는 기능을 사용 못함
    - 준영속 상태로 만드는 방법
        - em.detach() : 특정 엔티티만 준영속 상태로 변환
        - em.clear() : 영속성 컨텍스트를 완전히 초기화
        - em.close() : 영속성 컨텍스트를 종료
    ```java
          Member member = em.find(Member.class, 150L);
          member.setName("AAAA");
  //        em.clear();
  //        em.close();
          em.detach(member);
    ```
    
- 정리
    - 엔티티 매니저는 엔티티 매니저 팩토리를 통해 생성할 수 있다.
    - 엔티티 매니저 팩토리는 애플리 케이션 당 하나만 생성을 해야하며 엔티티 매니저는 스레드간 공유해서는 안된다.
    - 엔티티 매니저를 사용하면 영속성 컨텍스트를 통해 엔티티를 관리하게 되는데 1차캐시, 동일성 보장, 쓰기지연, 변경감지, 지연 로딩 등을  제공한다.
    - 영속성 컨텍스트에 저장한 엔티티는 flush 되어야만 데이터베이스에 반영 된다.
    

- 객체와 테이블 매핑
    - @Entity
        - jpa가 관리하는 엔티티
        - jpa를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
        - 기본 생성자 필수
        - final 클래스, enum,interface,inner 클래스 사용X
        - 저장할 필드에 final 사용X
    - @Table
        - name : 매핑할 테이블 이름
        - catalog : 데이터베이스 catalog 매핑
        - schema : 데이터베이스 schema 매핑
        - uniqueConstraints : DDL 생성 시에 유니크 제약조건 생성
    
- 데이터베이스 스키마 자동 생성
    - 애플리케이션 실행 시점에 db테이블을 생성해주는 기능 제공( 운영에서 사용 x )
    - 테이블 중심 -> 객체중심
    - 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 ddl 생성
    - 이렇게 생성된 ddl은 개발 장비에서만 사용
    - 생성된 ddl은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용
    - create : 애플리케이션 실행시 존재하는 기존 테이블을 drop하고 다시 create 함 (drop + create)
    - create-drop : create와 같으나 종료시점에 테이블 drop
    - update : 변경부분만 반영(운영db사용x)
    - validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
    - none : 사용하지 않음
    ```xml
      <property name="hibernate.hbm2ddl.auto" value="create" />
    ```
    - 운영장비에는 절대 create, create-drop, update 사용하면 안된다.
    - 개발 초기단계는 create 또는 update
    - 테스트서버는 update 또는 validate
    - 스테이징과 운영서버는 validate 또는 none
    - ddl 생성기능
        - 제약조건 추가 
            - @Column(name = "username", unique = true, length = 10)
        - ddl 생성 기능은 ddl을 자동 생성할때만 사용되고 jpa의 실행 로직에는 영향을 주지 않는다.
        
- 필드와 컬럼 매핑
```java
    //컬럼맵핑
    @Column(name = "name")
    private String username;

    private Integer age;

    //enum 타입 매핑
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    //날짜 타입 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //BLOB, CLOB 매핑
    @Lob
    private String description;

    //특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
    @Transient
    private  int temp;
```


- @Column

| 속성 | 설명 | 기본값 |
|---|---|---|
| name | 필드와 매핑할 테이블의 컬럼 이름 | 객체의 필드 이름 | 
| insertable, updatable | 등록, 변경 가능 여부 | TRUE | 
| nullable(DDL)  | null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다. |  | 
| unique(DDL)  | @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.(제약조건 이름이 이상하게 주어지기때문에 운영 사용 X) | | 
| columnDefinition(DDL)  | 데이터베이스 컬럼 정보를 직접 줄 수 있다. ex) varchar(100) default ‘EMPTY' | 필드의 자바 타입과방언 정보를 사용해 | 
| length(DDL)  | 문자 길이 제약조건, String 타입에만 사용한다. | 255 | 
| precision, scale(DDL)   | BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다). precision은 소수점을 포함한 전체 자 릿수를, scale은 소수의 자릿수다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나정 밀한 소수를 다루어야 할 때만 사용한다. | precision=19, scale=2  | |

- @Enumerated
    - ordinal 사용 비추천 무조건 String로 쓸것. enum 타입 추가시 순서가 변경되면 기준 코드가 달라짐.
    - EX) RoleType.USER로 권한 셋팅시 RoleType 0으로 INSERT 하지만, USER 앞에 GUEST를 추가시 RoleType에 선언된 순서에 따라 GUEST가 0으로 INSERT 됨.
    
    ```java 
            public enum RoleType {
        //        GUEST,
                USER,
                ADMIN
            }
        
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
                        Member member = new Member();
                        member.setId(1L);
                        member.setUsername("A");
                        member.setRoleType(RoleType.USER);
            
                        em.persist(member);
            
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
    ```
  
  
| 속성 | 설명 | 기본값 |
|---|---|---|
| value | • EnumType.ORDINAL: enum 순서를 데이터베이스에 저장 <br /> • EnumType.STRING: enum 이름을 데이터베이스에 저장 | EnumType.ORDINAL | 
 
- @Temporal
    - 날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용
    - LocalDate, LocalDateTime을 사용할 때는 생략 가능(최신 하이버네이트 지원)
    ```java 
        //년월만, date 타입 으로 저장 
        private LocalDate testLocalDate;
    
        //년월일 다 포함, timestamp 타입
        private LocalDateTime testLocalDateTime;
    ```
    
| 속성 | 설명 | 기본값 |
|---|---|---|
| value | • TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑(예: 2013–10–11) <br /> • TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑(예: 11:11:11) <br /> • TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스 timestamp 타입과 매핑(예: 2013–10–11 11:11:11)  | EnumType.ORDINAL | 

- @Lob
    - 데이터베이스 BLOB, CLOB 타입과 매핑 
    - @Lob에는 지정할 수 있는 속성이 없다.
    - 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
    
- 기본키 매핑 어노테이션
    - @Id
    - @GeneratedValue

- 기본키 매핑 방법
    - 직접할당 : @Id 사용
    - 자동생성 : @GeneratedValue
        - IDENTITY: 데이터베이스에 위임, MYSQL(auto increment)
        - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE 
            - @SequenceGenerator 필요
        - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
            - @TableGenerator 필요
        - AUTO: 방언에 따라 자동 지정, 기본값
        
- IDENTITY 전략
    - 기본 키 생성을 데이터베이스에 위임 (예: MySQL의 AUTO_ INCREMENT)
    - JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
    - AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
    - IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회(해당시점에 id를 알수있음)

- SEQUENCE 전략
    - 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트
    - em.persist(member) 전에 call next value for member_seq > 시퀀스를 얻어옴
    - allocationSize 기본값은 50 시퀀스 1~51까지 미리 생성하고 메모리에서 꺼내어 사용
    - 데이터베이스 시퀀스값은 50씩 증가하지만 애플리케이션 내에서 동작할때는 메모리에서 꺼내어 사용하기때문에 1씩 증가
```java
    @Entity
    @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq", allocationSize = 50)
    public class Member {
    
        //jpa에 pk를 알려주어야 함
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
        private Long id;
```

```java
   Member member = new Member();
   member.setUsername("A");
   member.setRoleType(RoleType.USER);

   Member member2 = new Member();
   member2.setUsername("B");
   member2.setRoleType(RoleType.USER);

   Member member3= new Member();
   member3.setUsername("C");
   member3.setRoleType(RoleType.USER)
   System.out.println("=======================");

   em.persist(member); //SEQUENCE 전략 1,51
   em.persist(member2); //MEM
   em.persist(member3); //MEM

   System.out.println("member1 : " + member.getId()); //1
   System.out.println("member2 : " + member2.getId()); //2
   System.out.println("member3 : " + member3.getId()); //3

   System.out.println("=======================");
```

```java
  @Entity 
  @SequenceGenerator( 
   name = "MEMBER_SEQ_GENERATOR", 
   sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
   initialValue = 1, allocationSize = 1) 
  public class Member { 
   @Id 
   @GeneratedValue(strategy = GenerationType.SEQUENCE, 
   generator = "MEMBER_SEQ_GENERATOR") 
   private Long id; 
```
   - @SequenceGenerator - 속성
   
   | 속성 | 설명 | 기본값 |
   |---|---|---|
   | name | 식별자 생성기 이름  | 필수 | 
   | sequenceName | 데이터베이스에 등록되어 있는 시퀀스 이름  | hibernate_sequence | 
   | initialValue | DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는 수를 지정한다  | 1 | 
   | allocationSize | 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨 데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다  | 50 | 
   | catalog, schema | 데이터베이스 catalog, schema 이름  |  | 
   
   
- TABLE 전략
    - 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
    - 운영 권장 X 잘 안씀.
```java
    @Entity 
    @TableGenerator( 
     name = "MEMBER_SEQ_GENERATOR", 
     table = "MY_SEQUENCES", 
     pkColumnValue = "MEMBER_SEQ", allocationSize = 1) 
    public class Member { 
     @Id 
     @GeneratedValue(strategy = GenerationType.TABLE, 
     generator = "MEMBER_SEQ_GENERATOR") 
     private Long id;
```
   - @TableGenerator - 속성
   
   | 속성 | 설명 | 기본값 |
   |---|---|---|
   | name | 식별자 생성기 이름  | 필수 | 
   | table | 키생성 테이블명  | hibernate_sequence | 
   | pkColumnName | 시퀀스 컬럼명  | next_val | 
   | pkColumnValue | 키로 사용할 값 이름 | 엔티티 이름 | 
   | initialValue | 초기 값, 마지막으로 생성된 값이 기준이다.  | 0 | 
   | allocationSize | 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨)  | 50 | 
   | catalog, schema | 데이터베이스 catalog, schema 이름  |  | 
   | uniqueConstraints(DDL) | 유니크 제약 조건을 지정할 수 있다.   |  | 

- 권장하는 식별자 전략
    - 기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
    - 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자. 
    - 예를 들어 주민등록번호도 기본 키로 적절하기 않다. 
    - 권장: Long형 + 대체키 + 키 생성전략 사용


 
    