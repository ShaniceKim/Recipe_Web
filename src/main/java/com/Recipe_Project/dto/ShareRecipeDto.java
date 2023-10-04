package com.Recipe_Project.dto;

import com.Recipe_Project.entity.Share;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;



@Getter
@Setter
public class ShareRecipeDto { //shop 의 mainItemDto 랑 같음.
    private Long id; //레시피 아이디(pk)
    private String foodNm; //레시피 이름
    private String category; //카테고리
    private String imgUrl;
    private String recipe;
    private String ingredients;

    @QueryProjection //Querydsl 결과 조회 시 MainItemDto 객체로 바로 오도록  활용
    public ShareRecipeDto(Long id, String foodNm, String imgUrl, String category, String recipe, String ingredients){
        this.id = id;
        this.foodNm = foodNm;
        this.imgUrl = imgUrl;
        this.category = category;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

}