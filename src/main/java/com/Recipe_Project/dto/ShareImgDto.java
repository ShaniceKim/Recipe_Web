package com.Recipe_Project.dto;

import com.Recipe_Project.entity.ShareImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ShareImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();
    public static ShareImgDto of(ShareImg shareImg){
        return modelMapper.map(shareImg, ShareImgDto.class);
    }
}
