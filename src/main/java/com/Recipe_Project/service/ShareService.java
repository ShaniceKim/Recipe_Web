package com.Recipe_Project.service;

import com.Recipe_Project.dto.*;
import com.Recipe_Project.entity.Share;
import com.Recipe_Project.entity.ShareImg;
import com.Recipe_Project.repository.ShareImgRepository;
import com.Recipe_Project.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Component
public class ShareService {

    private final ShareRepository shareRepository;

    private final ShareImgService shareImgService;

    private final ShareImgRepository shareImgRepository;







    public Long saveShare(ShareFormDto ShareFormDto, List<MultipartFile> ShareImgFileList)
            throws Exception {
        // 공유하기 게시물 등록
        Share share = ShareFormDto.createShare();
        shareRepository.save(share);
        //음식이미지 등록
        for(int i=0;i<ShareImgFileList.size();i++){
            ShareImg shareImg = new ShareImg();
            shareImg.setShare(share);
            if(i==0)
                shareImg.setRepImgYn("Y");
            else
                shareImg.setRepImgYn("N");
            shareImgService.saveShareImg(shareImg,ShareImgFileList.get(i));
        }
        return share.getId();
    }


    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getChinesePage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getChinesePage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getDessertPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getDessertPage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getDietPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getDietPage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getDrinksPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getDrinksPage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getJapanesePage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getJapanesePage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getKoreanPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getKoreanPage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getVeganPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getVeganPage(shareSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getWesternPage(ShareSearchDto shareSearchDto,Pageable pageable){
        return shareRepository.getWesternPage(shareSearchDto,pageable);
    }


    @Transactional(readOnly = true)
    public Page<Share> getAdminItemPage(ShareSearchDto shareSearchDto, Pageable pageable){
        return shareRepository.getAdminItemPage(shareSearchDto,pageable);
    }


    @Transactional(readOnly = true)
    public ShareFormDto getShareDtl(Long shareId){
        List<ShareImg> shareImgList = shareImgRepository.findByShareIdOrderByIdAsc(shareId); // DB 에서 데이터를 가져옴
        List<ShareImgDto> shareImgDtoList = new ArrayList<>(); // 왜 DTO 를 만들었나요?

        for(ShareImg shareimg : shareImgList){
            ShareImgDto shareImgDto = ShareImgDto.of(shareimg);
            shareImgDtoList.add(shareImgDto);
        }

        Share share = shareRepository.findById(shareId).orElseThrow(EntityNotFoundException::new);
        ShareFormDto shareFormDto = ShareFormDto.of(share);
        shareFormDto.setShareImgDtoList(shareImgDtoList);
        return shareFormDto;
    }

    @Transactional(readOnly = true)
    public Page<ShareRecipeDto> getMainItemPage(ShareSearchDto shareSearchDto, Pageable pageable){
        return shareRepository.getMainItemPage(shareSearchDto, pageable);
    }

    @Transactional
    public Long updateShare(ShareFormDto shareFormDto, List<MultipartFile> shareImgFileList) //수정
            throws Exception{
        Share share = shareRepository.findById(shareFormDto.getId()).
                orElseThrow(EntityNotFoundException::new);
        share.updateShare(shareFormDto);
        List<Long> shareImgIds = shareFormDto.getShareImgIds();
        for(int i =0; i<shareImgFileList.size();i++){
            shareImgService.updateShareImg(shareImgIds.get(i), shareImgFileList.get(i));
        }
        return share.getId();
    }


    public void deleteRecipe(Long shareId) {
        shareRepository.deleteById(shareId);
    }











}

