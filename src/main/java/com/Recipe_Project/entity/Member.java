package com.Recipe_Project.entity;

import com.Recipe_Project.constant.Role;
import com.Recipe_Project.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity //나는 엔티티야. 디비에서 쓸 테이블을 여기서 관리해
@Table(name = "member") //디비에 회원 테이블 이름은 member 야.
@Getter
@Setter
@ToString
public class Member extends BaseEntity{ //BaseEntity 로 생성일, 수정일 자동화
    @Id //기본 키 (PK) 와 필드를 매핑
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO) //기본 키 (PK) 자동생성해주는 어노테이션
    private Long id; //아이디

    @Column(unique = true)
    private String nickname; //닉네임

    @Column(unique = true) //동일한 값이 db에 들어올 수 없도록 unique 속성 지정해줌.
    private String email;//이메일

    private String password;//비밀번호


    @Enumerated(EnumType.STRING)
    private Role role;
    //멤버 엔티티에 회원을 생성하는메소드





    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        //자료형이 Member 이고, createMember 메소드의 매개변수는 memberformDto, passwordEncoder
        Member member = new Member(); //멤버 객체 생성
        member.setNickname(memberFormDto.getNickname()); //memberFormDto 에서 닉네임 get 해와서 엔티티에 set 해줌.
        member.setEmail(memberFormDto.getEmail());//memberFormDto 에서 이메일 get 해와서 엔티티에 set 해줌.
        //passwordEncoder.encode -> 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword()); //memberFormDto 에서 비밀번호 받아와서 password라는 변수에 넣어줌.
        member.setPassword(password);//member  객체에 비밀번호를 설정해줌. 무엇을? 바로 위에있는 디티오에서 받아온 비밀번호를.
        member.setRole(Role.USER);
        return member;
    }

    public void updatemember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        this.nickname = memberFormDto.getNickname();
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        this.password=password;
    }


}
