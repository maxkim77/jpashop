package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MemberRepository {
    /*MemberRepository 클래스입니다. 이 클래스는 Member 엔티티를 데이터베이스에 저장하고, ID를 통해 Member 엔티티를 검색하는 기능을 제공*/
    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class ,id);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
