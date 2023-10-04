package com.Recipe_Project.service;


import com.Recipe_Project.dto.MemberFormDto;
import com.Recipe_Project.entity.Member;
import com.Recipe_Project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
//Service 란? 회원가입, 조회 등의 비즈니스 단 기능들이 수행 되는곳. 중복 검사 등도 이곳에서 실행된다.
// 단 직접 db에 접근하진 않는다. (repository 를 거쳐 db에 접근하기때문.)
@Transactional
//모든 작업들이 성공적으로 완료되어야 작업 묶음의 결과를 적용하고, 어떤 작업에서 오류가 발생했을 때는 이전에 있던 모든 작업들이 성공적이었더라도 없었던 일처럼 완전히 되돌리는것이 트랜잭션의 개념이다.
//데이터베이스를 다룰때 트랜잭션을 적용하면 데이터추가, 갱신, 삭제 등으로 이루어진 작업을 처리하던 중 오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다. 모든 작업들이 성공해야만 최졷적으로 데이터베이스에 반영하도록 한다.
@RequiredArgsConstructor //생성자 자동생성
public class MemberService implements UserDetailsService {
    //UserDetailsService 인터페이스는 데이터베이스에서 회원정보를 가져오는 역할을 담당
    //메소드로는 loadUserByUsername() 가 존재하며 회원 정보를 조회하여 사용자의 정보와 권한을 갖는
    //UserDetails 인터페이스를 반환합니다.
    private final MemberRepository memberRepository; //멤버리포지토리 선언

    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) { //데이터베이스에 저장할 멤버
        validateDuplicateMember(member);
        return memberRepository.save(member); //데이터베이스에 저장하라고 리포지토리를 리턴.
    }


    private void validateDuplicateMember(Member member) { //중복회원 검증 메소드
        Member findMember = memberRepository.findByEmail(member.getEmail()); //리포지토리 통해서 이메일 찾기.
        if (findMember != null) { //리포지토리에서 찾은 멤버가 null 이 아니다? 그렇다면 이미 가입되있다는 뜻!
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //UserDetailsService 상속 받으면 반드시 loadUserByUsername(String) 메소드를 오버라이딩 해야함.
        //이 메소드에서 DB 로부터 회원정보를 가져와 있는 회원인지 없는 회원인지 체크 여부를 확인하는 중요한 메소드.
        //사용자명으로 비밀번호를 조회하여 리턴하는 메서드
        Member member = memberRepository.findByEmail(email);


        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }

    public int findByNickname(String username) {
        Member member = memberRepository.findByNickname(username);
        if (member == null) {
            System.out.println("사용가능한 닉네임 입니다.");
            return 1;
        } else {
            System.out.println("사용불가한 닉네임 입니다.");
            return 0;
        }

    }

    public MemberFormDto getmember(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }

    public void memberupdate(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        member.updatemember(memberFormDto, passwordEncoder);
    }




    public boolean memberdelete(MemberFormDto memberFormDto, String password) {
        if (passwordEncoder.matches(password, memberFormDto.getPassword())) {
            memberRepository.deleteById(memberFormDto.getId());
            return true;
        } else {
            return false;
        }
    }

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\Recipe1\\profile" + file.getOriginalFilename()));
    }

}
