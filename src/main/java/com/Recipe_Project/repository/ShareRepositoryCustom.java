package com.Recipe_Project.repository;


import com.Recipe_Project.dto.ShareRecipeDto;
import com.Recipe_Project.dto.ShareSearchDto;
import com.Recipe_Project.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShareRepositoryCustom {
    Page<Share> getAdminItemPage(ShareSearchDto shareSearchDto, Pageable pageable);

    //Page<ShareRecipeDto> getMainItemPage(ShareSearchDto shareSearchDto, Pageable pageable);

        Page<ShareRecipeDto>getChinesePage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getDessertPage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getDietPage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getDrinksPage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getJapanesePage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getKoreanPage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getVeganPage(ShareSearchDto shareSearchDto, Pageable pageable);
        Page<ShareRecipeDto>getWesternPage(ShareSearchDto shareSearchDto, Pageable pageable);

        Page<ShareRecipeDto> getMainItemPage(ShareSearchDto shareSearchDto, Pageable pageable);

    }

