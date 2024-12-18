package com.ssestudy.goforitkanglogin.dao;

import com.ssestudy.goforitkanglogin.dto.MemberDTO;
import com.ssestudy.goforitkanglogin.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;


    @Transactional
    public void save(Member member) {
        try{
            em.persist(member);
        }catch (PersistenceException e){
            e.printStackTrace();
            new RuntimeException("오류야");
        }
    }
    @Transactional
    public Member login(String email) {
        try{
            return (Member) em.createQuery("select m from Member m where m.email = :email")
                    .setParameter("email",email)
                    .getSingleResult();
        }catch (PersistenceException e){
            e.printStackTrace();
        }
        return null;
    }
}
