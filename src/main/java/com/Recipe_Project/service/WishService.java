//package com.Recipe_Project.service;
//
//import com.Recipe_Project.dto.ShareRecipeDto;
//import com.Recipe_Project.dto.WishDetailDto;
//import com.Recipe_Project.entity.Member;
//import com.Recipe_Project.entity.Share;
//import com.Recipe_Project.entity.Wish;
//import com.Recipe_Project.entity.WishRecipe;
//import com.Recipe_Project.repository.MemberRepository;
//import com.Recipe_Project.repository.ShareRepository;
//import com.Recipe_Project.repository.WishRecipeRepository;
//import com.Recipe_Project.repository.WishRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.thymeleaf.util.StringUtils;
//
//import javax.persistence.EntityExistsException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//
//public class WishService {
//
//    private final ShareRepository shareRepository;
//
//    private final MemberRepository memberRepository;
//
//    private final WishRepository wishRepository;
//
//    private final WishRecipeRepository wishRecipeRepository;
//
//    private final WishService wishService;
//
//    public Long addWish(ShareRecipeDto shareRecipeDto, String email) {
//        Share share = shareRepository.findById(shareRecipeDto.getId())
//                .orElseThrow(EntityExistsException::new);
//        Member member = memberRepository.findByEmail(email);
//
//        Wish wish = wishRepository.findByMemberId(member.getId());
//        if (wish == null) {
//            wish = Wish.createWish(member);
//            wishRepository.save(wish);
//        }
//
//        WishRecipe savedWishRecipe = wishRecipeRepository
//                .findByWishIdAndShareId(wish.getId(), share.getId());
//        if (savedWishRecipe != null) {
//            return savedWishRecipe.getId();
//        } else {
//            WishRecipe wishRecipe = WishRecipe
//                    .createWishRecipe(wish, share);
//            wishRecipeRepository.save(wishRecipe);
//            return wishRecipe.getId();
//        }
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<WishDetailDto> getWishList(String email) {
//        List<WishDetailDto> wishDetailDtoList = new ArrayList<>();
//
//        Member member = memberRepository.findByEmail(email);
//
//        Wish wish = wishRepository.findByMemberId(member.getId());
//        if (wish == null) {
//            return wishDetailDtoList;
//        }
//        wishDetailDtoList = wishRecipeRepository
//                .findWishDetailDtoList(wish.getId());
//        return wishDetailDtoList;
//
//    }
//
//    @Transactional(readOnly = true)
//    public boolean validateWishRecipe(Long wishRecipeId, String email) {
//        Member curMember = memberRepository.findByEmail(email);
//        WishRecipe wishRecipe = wishRecipeRepository.findById(wishRecipeId).orElseThrow(EntityExistsException::new);
//        Member savedMember = wishRecipe.getWish().getMember();
//
//
//        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
//            return false;
//        }
//        return true;
//    }
//
//
//    public void deleteWishRecipe(Long wishRecipeId) {
//        WishRecipe wishRecipe = wishRecipeRepository.findById(wishRecipeId)
//                .orElseThrow(EntityExistsException::new);
//        wishRecipeRepository.delete(wishRecipe);
//    }
//
//
//}
