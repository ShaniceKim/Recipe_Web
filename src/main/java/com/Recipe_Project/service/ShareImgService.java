package com.Recipe_Project.service;


import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.entity.ShareImg;
import com.Recipe_Project.repository.ShareImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShareImgService {
    @Value("${itemImgLocation}") // application.properties 에 itemImgLocation
    private String itemImgLocation;

    private final ShareImgRepository shareImgRepository;
    private final FileService fileService;

    public boolean deleteRecipeImg(ShareFormDto shareFormDto) {
        Optional<ShareImg> shareImg = shareImgRepository.findById(shareFormDto.getId());
        return true;
    }


    public void saveShareImg(ShareImg shareImg, MultipartFile shareImgFile) throws Exception {
        String oriImgName = shareImgFile.getOriginalFilename(); // 원래 이미지 경로
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName); // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) { // oriImgName 문자열로 비어있지 않으면 실행
            System.out.println("******");
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, shareImgFile.getBytes());

            System.out.println(imgName);
            imgUrl = "/images/item/" + imgName;
        }
        // 상품 이미지 정보 저장
        // oriName : 레시피 이미지 파일의 원래 이름
        // imgName : 실제 로컬에 저장된 레시피 이미지 파일의 이름
        // imgUrl : 로컬에 저장된 레시피 이미지 파일을 불러오는 경로
        shareImg.updateShareImg(oriImgName, imgName, imgUrl);
        shareImgRepository.save(shareImg);
    }

    public void updateShareImg(Long shareImgId, MultipartFile shareImgFile) throws Exception {
        if (!shareImgFile.isEmpty()) { // 레시피 이미지를 수정한 경우 레시피 이미지 업데이트
            ShareImg savedShareImg = shareImgRepository.findById(shareImgId)
                    .orElseThrow(EntityExistsException::new); // 기존 엔티티 조회
            // 기존에 등록된 레시피 이미지 파일이 있는경우 파일 삭제
            if (!StringUtils.isEmpty(savedShareImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedShareImg.getImgName());
            }
            String oriImgName = shareImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, shareImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/item/" + imgName;
            // 변경 된 레시피 이미지 정보를 해킹
            // 레시피 등록을 하는 경우에는 ItemImgRepository.save()로직을 호출 하지만 호출을 하지 않습니다.
            //

            savedShareImg.updateShareImg(oriImgName, imgName, imgUrl);
        }
    }

//    public void deleteShareImg(Long shareImgId, MultipartFile shareImgFile) throws Exception {
//        if (!shareImgFile.isEmpty()) { // 레시피 이미지를 수정한 경우 레시피 이미지 업데이트
//            ShareImg savedShareImg = shareImgRepository.findById(shareImgId)
//                    .orElseThrow(EntityExistsException::new); // 기존 엔티티 조회
//            // 기존에 등록된 레시피 이미지 파일이 있는경우 파일 삭제
//            if (!StringUtils.isEmpty(savedShareImg.getImgName())) {
//                fileService.deleteFile(itemImgLocation + "/" + savedShareImg.getImgName());
//            }
//            savedShareImg.deleteShareImg(oriImgName, imgName, imgUrl);
//
//        }
//    }
}
