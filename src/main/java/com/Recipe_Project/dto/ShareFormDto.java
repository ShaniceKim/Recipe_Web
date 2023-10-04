package com.Recipe_Project.dto;


import com.Recipe_Project.entity.Share;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShareFormDto {

    private Long id; //pk 값

    private String category; // 카테고리


    @NotBlank(message = "음식이름을 입력해주세요.")
    private String foodNm; //음식이름

    @NotBlank(message = "재료들을 작성해주세요.")
    private String ingredients; //재료들

    @NotBlank(message = "레시피를 작성해주세요.")
    private String recipe; //레시피


    private List<ShareImgDto> shareImgDtoList = new ArrayList<>(); // 음식 이미지 정보

    private List<Long> shareImgIds = new ArrayList<>(); // 음식 이미지 아이디 수정용

    private List<Long> deleteImgIds = new ArrayList<>();

//   private List<Long> deleteById = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Share createShare(){ //공유 메소드
        return modelMapper.map(this, Share.class); // ShareFormDto -> Share 연결
    }

    public static ShareFormDto of(Share share){
        return modelMapper.map(share, ShareFormDto.class); // Share -> ShareFormDto 연결
    }
}