package com.Recipe_Project.repository;

import com.Recipe_Project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> { //첫번째는 엔티티의 클래스명, 두번째는 PK의 자료형
    Member findByEmail(String email);

    //Repository 란, 직접 DB 를 건드는 애들이다. save, delete, find 등의 일이 수행된다.

    Member findByNickname(String nickname);
}
