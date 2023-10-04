package com.Recipe_Project.controller;

import com.Recipe_Project.dto.MemberFormDto;
import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.dto.ShareRecipeDto;
import com.Recipe_Project.dto.ShareSearchDto;
import com.Recipe_Project.entity.Share;
import com.Recipe_Project.service.MemberService;
import com.Recipe_Project.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor //생성자 자동생성 해주는 어노테이션

public class MypageController {

    private final MemberService memberService;
    private final ShareService shareService;

    private final PasswordEncoder passwordEncoder; //passwordEncoder 필드

    @GetMapping(value = "/mypage/mypage")//마이페이지 화면 보여주기
    public String myPage(Model model, Principal principal) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto = memberService.getmember(principal.getName());
        model.addAttribute("memberFormDto", memberFormDto);
        return "mypage/myPage";
    }

//    @PostMapping(value = "/mypage/uploadProfile")
//    public String uploadProfile(@RequestPart MultipartFile file) throws IOException {
//        memberService.uploadFile(file);
//        return "redirect:/";
//
//    }





    @GetMapping(value = {"/mypage/myrecipe", "/mypage/myrecipe/{page}"})
    public String myrecipe(ShareSearchDto shareSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        if (page.isPresent()) {
            System.out.println(page.get());
        }
        System.out.println(shareSearchDto.getSearchDateType());
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Share> shares = shareService.getAdminItemPage(shareSearchDto, pageable);
        //shares.getNumber(); 현재페이지
        model.addAttribute("shares", shares);
        model.addAttribute("shareSearchDto", shareSearchDto);
        model.addAttribute("maxPage", 10);
        return "mypage/myRecipe";
    }







//    @GetMapping(value = {"/mypage/wishList", "/mypage/wishList/{page}"})
//    public String wishList(ShareSearchDto shareSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
//        if (page.isPresent()) {
//            System.out.println(page.get());
//        }
//        System.out.println(shareSearchDto.getSearchDateType());
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
//        Page<Share> shares = shareService.getAdminItemPage(shareSearchDto, pageable);
//        //shares.getNumber(); 현재페이지
//        model.addAttribute("shares", shares);
//        model.addAttribute("shareSearchDto", shareSearchDto);
//        model.addAttribute("maxPage", 10);
//        return "mypage/wishList";
//    }


}