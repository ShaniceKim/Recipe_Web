//package com.Recipe_Project.entity;
//
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "wish")
//@Getter
//@Setter
//@ToString
//public class Wish {
//    @Id
//    @Column(name="share_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @OneToOne(targetEntity = Member.class)
//    @JoinColumn(name = "member_id")
//
//    private String foodNm; // 음식이름
//
//    private String ingredients; // 재료들
//
//    private String recipe; // 레시피
//
//    @CreatedDate // 생성시 자동 저장
//    @Column(updatable = false)
//    private LocalDateTime regTime; // 등록 시간
//    private LocalDateTime updateTime; // 수정 시간
//
//
//    private Member member;
//
//    public static Wish createWish(Member member){
//        Wish wish = new Wish();
//        wish.setMember(member);
//        return wish;
//    }
//}
