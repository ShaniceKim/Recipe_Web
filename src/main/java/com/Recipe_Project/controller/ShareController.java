package com.Recipe_Project.controller;

import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.dto.ShareRecipeDto;
import com.Recipe_Project.dto.ShareSearchDto;
import com.Recipe_Project.entity.Share;
import com.Recipe_Project.repository.ShareRepository;
import com.Recipe_Project.service.ShareImgService;
import com.Recipe_Project.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller // 나는 컨트롤러얌
@RequiredArgsConstructor
//@RequestMapping("/share") // 공유하기 매핑
public class ShareController {

    private final ShareService shareService;
    private final ShareImgService shareImgService;
    private ShareRepository shareRepository;

//    @GetMapping(value = "/share/{shareId}") //레시피 상세페이지
//    public String shareDetail(Model model, @PathVariable("shareId")Long shareId){
//        ShareFormDto shareFormDto = shareService.getShareDtl(shareId);
//        model.addAttribute("share", shareFormDto);
//        return "share/shareDetail";
//    }

    @GetMapping(value = "/share/{shareId}") //레시피 상세페이지
    public String shareDetail(Model model, @PathVariable("shareId") Long shareId) {
        ShareFormDto shareFormDto = shareService.getShareDtl(shareId);
        model.addAttribute("share", shareFormDto);
        return "share/shareDetail";
    }

    @GetMapping(value = "/share/posting") // 공유하기 페이지
    public String shareForm(Model model) {
        model.addAttribute("shareFormDto", new ShareFormDto());
        //ShareForm.html 에서 th:object="${ShareFormDto}" 로 설정되어있는 폼안에있는 데이터를 new ShareFormDto()에 담음
        return "share/shareForm";
    }

    @PostMapping(value = "/share/posting") //ShareFormDto 에서 객체받은게 내려옴
    public String sharePosting(@Valid ShareFormDto ShareFormDto, BindingResult bindingResult,
                               Model model,
                               @RequestParam("shareImgFile") List<MultipartFile> shareImgFileList) {
        if (bindingResult.hasErrors()) { //데이터검증
            return "share/shareForm";
        }

        if (shareImgFileList.get(0).isEmpty() && ShareFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 음식 이미지는 필수 입력 값입니다.");
            return "share/shareForm";
        }
        try {
            shareService.saveShare(ShareFormDto, shareImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "레시피 등록 중 에러가 발생하였습니다.");
            return "share/shareForm";
        }
        return "redirect:/";
    }


    @GetMapping(value = "/posting/{shareId}")
    public String shareDtl(Model model, @PathVariable("shareId") Long shareId) {
        ShareFormDto shareFormDto = shareService.getShareDtl(shareId);
        model.addAttribute("share", shareFormDto);
        return "redirect:/";
    }


    @GetMapping(value = "/share/posting/{shareId}")
    // 마이페이지 내가쓴글 리스트에서 음식명하이픈을 누르면 가는 수정페이지(share/posting/{shareId}) 컨트롤
    public String shareDtl(@PathVariable("shareId") Long shareId, Model model) {
        try {
            ShareFormDto shareFormDto = shareService.getShareDtl(shareId);
            model.addAttribute("shareFormDto", shareFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 레시피 입니다.");
            model.addAttribute("shareFormDto", new ShareFormDto());
            return "share/shareForm";
        }
        return "share/shareForm";
    }

    @PostMapping(value = "/share/posting/{shareId}") // 수정
    public String shareUpdate(@Valid ShareFormDto shareFormDto, BindingResult bindingResult,
                              @RequestParam("shareImgFile") List<MultipartFile> shareImgFileList,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "share/shareForm";
        }
        if (shareImgFileList.get(0).isEmpty() && shareFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값입니다.");
            return "share/shareForm";
        }
        try {
            shareService.updateShare(shareFormDto, shareImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "share/shareForm";
        }

        return "redirect:/";
    }
    @PostMapping(value = "/share/deleteRecipe/{shareId}") //레시피 삭제 페이지
    public String deleteRecipe(ShareFormDto shareFormDto,@PathVariable("shareId")Long shareId) {
        boolean result = shareImgService.deleteRecipeImg(shareFormDto);
        if(result){
            shareService.deleteRecipe(shareId);
            return "redirect:/";
        }
        else{
            return  "share/shareForm";
        }
    }

}


