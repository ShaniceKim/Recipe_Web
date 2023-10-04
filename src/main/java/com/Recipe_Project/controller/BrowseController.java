package com.Recipe_Project.controller;


import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.dto.ShareRecipeDto;
import com.Recipe_Project.dto.ShareSearchDto;
import com.Recipe_Project.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller // 나는 컨트롤러얌
@RequiredArgsConstructor
@RequestMapping("/browse")
public class BrowseController {

    private final ShareService shareService;




    @GetMapping(value = "/category") //카테고리페이지
    public String browseForm() { // 보러가기창 매핑
        return "/browse/browseForm";
    }

    @GetMapping(value = "/recipeSearch")
    public String recipeSearch(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getMainItemPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",10);
        return "/view/recipeSearch";
    }



    @GetMapping(value = "/category/viewKorean")
    public String ViewKForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getKoreanPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",10);
        return "/view/viewKorean";
    }

    @GetMapping(value = "/category/viewChinese") //중식레시피 페이지 를 줌
    public String ViewCForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getChinesePage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewChinese";
    }

    @GetMapping(value = "/category/viewWestern") //양식페이지
    public String ViewWForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getWesternPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewWestern";
    }

    @GetMapping(value = "/category/viewJapanese") //일식페이지
    public String ViewJForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getJapanesePage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewJapanese";
    }

    @GetMapping(value = "/category/viewVegan") //비건페이지
    public String ViewVForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getVeganPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewVegan";
    }

    @GetMapping(value = "/category/viewDiet") //다이어트페이지
    public String ViewDForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getDietPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewDiet";
    }

    @GetMapping(value = "/category/viewDessert") //디저트페이지
    public String ViewDsForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getDessertPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",10);
        return "/view/viewDessert";
    }

    @GetMapping(value = "/category/viewDrinks") //음료페이지
    public String ViewDrForm(ShareSearchDto shareSearchDto,Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        if(shareSearchDto.getSearchQuery() == null) //검색
        {
            shareSearchDto.setSearchQuery("");
        }
        Page<ShareRecipeDto> shareRecipeDtos = shareService.getDrinksPage(shareSearchDto, pageable);
        model.addAttribute("share", shareRecipeDtos);
        model.addAttribute("itemSearchDto",shareSearchDto);
        model.addAttribute("maxPage",5);
        return "/view/viewDrinks";
    }

}