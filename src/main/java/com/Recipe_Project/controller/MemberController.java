package com.Recipe_Project.controller;

import com.Recipe_Project.dto.MemberFormDto;
import com.Recipe_Project.entity.Member;
import com.Recipe_Project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller //컨트롤러라고 알려주는 어노테이션
@RequestMapping("/members") // view 쪽이랑 연결해주는 매핑어노테이션
@RequiredArgsConstructor //생성자 자동생성 해주는 어노테이션

public class MemberController {

    private final MemberService memberService; //memberService 필드
    //final 은 값 재할당이 불가능.
    private final PasswordEncoder passwordEncoder; //passwordEncoder 필드

    @GetMapping(value = "/sign-up")
    public String memberForm(Model model) { //회원가입창 매핑
        model.addAttribute("memberFormDto", new MemberFormDto());
        //memberForm.html 에서 th:object="${memberFormDto}" 로 설정되어있는 폼안에있는 데이터를 new MemberFormDto()에 담음
        return "member/memberForm";
    }

    @PostMapping(value = "/sign-up")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        if(!memberFormDto.getPassword().equals(memberFormDto.getPasswordcheck())){
            bindingResult.rejectValue("passwordcheck","passwordIncorrect","비밀번호가 일치하지 않습니다.");
            return "member/memberForm";
        }

        try { //try 안에있는 소스들에서 에러나는지 확인. 정상작동하면 try-catch문 빠져나가서 redirect:/로 직행
            Member member = Member.createMember(memberFormDto, passwordEncoder); //에러나는지 확인 ->Entity
            memberService.saveMember(member); //중복체크했는데 중복 있는지 확인. 있으면 catch 문으로 넘어감. ->Service
        } catch (
                IllegalStateException e) { //이미 가입된 회원입니다. IllegalStateException e 가 발생했을때 이를 처리하기 위한 문장 IllegalStateException: 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때
            model.addAttribute("errorMessage", e.getMessage()); //->Service
            return "member/memberForm";
        }
       // memberService.saveMember(memberFormDto,request,response);
      //  result.loginMember(memberFormDto.getId(),memberFormDto.getPassword());
        return "redirect:/"; //메인화면 출력, 메인화면으로 리다이렉트 걸어주기
    }
    @GetMapping(value = "/login") //client 가 로그인 을 눌렀을때 여기로 옴. 로그인폼을 리턴해줌.
    public String loginMember() {
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error") // login/error 떴을때 모델객체 생성해주고 모델에 에러메세지 넣어서 프론트에 전달.
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @PostMapping(value = "/sign-up/{nickname}")
    @ResponseBody // ajax- json 형식으로 받음
    public int checkDuplicateName(@PathVariable("nickname") String nickname) {
        int result = memberService.findByNickname(nickname);
        return result;
    }

    @GetMapping(value = "/mypage")
    public String mypage(){
        return "/member/mypage";
    }

    @GetMapping(value = "/memberupdate")
    public String memberupdate(Model model, Principal principal) {
        MemberFormDto memberFormDto = memberService.getmember(principal.getName());
        model.addAttribute("memberupdate", memberFormDto);
        return "mypage/modifyProfile";
    }

    @PostMapping(value = "/memberupdate")
    public String memberupdate(MemberFormDto memberupdate) {
        memberService.memberupdate(memberupdate, passwordEncoder);
        return "mypage/myPage";
    }

    @GetMapping(value = "/deleteme")
    public String deleteme() {
        return "mypage/deleteMe";
    }

    @PostMapping(value = "/deleteme")
    public String deleteme(@RequestParam("password")String password,Principal principal){
        MemberFormDto memberFormDto = memberService.getmember(principal.getName());
        boolean result = memberService.memberdelete(memberFormDto,password);
        if(result){
            return "index";
        }
        else{
            return "mypage/deleteMe";
        }

    }
}