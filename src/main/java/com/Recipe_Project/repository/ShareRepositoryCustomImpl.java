package com.Recipe_Project.repository;


import com.Recipe_Project.dto.QShareRecipeDto;
import com.Recipe_Project.dto.ShareRecipeDto;
import com.Recipe_Project.entity.QShare;
import com.Recipe_Project.entity.QShareImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.Recipe_Project.dto.ShareSearchDto;
import com.Recipe_Project.entity.Share;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ShareRepositoryCustomImpl implements ShareRepositoryCustom{
    private JPAQueryFactory queryFactory; // 동적쿼리 사용하기 위해 JPAQueryFactory 변수 선언

    // 생성자
    public ShareRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory 실질적인 객체가 만들어 집니다.
    }

    private BooleanExpression foodNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QShare.share.foodNm.like("%"+searchQuery+"%");
    }
    @Override
    public Page<ShareRecipeDto> getMainItemPage(ShareSearchDto shareSearchDto, Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QMainItemDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(foodNmLike(shareSearchDto.getSearchQuery()))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }

    private  BooleanExpression regDtsAfter(String searchDateType){ // all, 1d, 1w, 1m 6m
        LocalDateTime dateTime = LocalDateTime.now(); // 현재시간을 추출해서 변수에 대입

        if(StringUtils.equals("all",searchDateType) || searchDateType == null){
            return null;
        }else if(StringUtils.equals("1d",searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if(StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m",searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m",searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        // 14
        return QShare.share.regTime.after(dateTime);
        //dateTime을 시간에 맞게 세팅 후 시간에 맞는 등록된 상품이 조회하도록 조건값 반환
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("foodNm",searchBy)){ // 상품명
            return QShare.share.foodNm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)){ // 작성자
            return QShare.share.createdBy.like("%"+searchQuery+"%");
        }
        else{
            return null;
        }

    }

    @Override
    public Page<Share> getAdminItemPage(ShareSearchDto shareSearchDto, Pageable pageable){
        QShare share = QShare.share;

        QueryResults<Share> results = queryFactory.selectFrom(QShare.share).
                where(regDtsAfter(shareSearchDto.getSearchDateType()),
                        searchByLike(shareSearchDto.getSearchBy(),shareSearchDto.getSearchQuery()))
                .orderBy(QShare.share.id.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<Share> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);

    }

    @Override
    public Page<ShareRecipeDto> getChinesePage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("CHINESE_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getDessertPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("DESSERT"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getDietPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("DIET_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }

    @Override
    public Page<ShareRecipeDto> getDrinksPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("DRINKS"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getJapanesePage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("JAPANESE_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getKoreanPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("KOREAN_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getVeganPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("VEGAN_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    @Override
    public Page<ShareRecipeDto> getWesternPage(ShareSearchDto shareSearchDto,Pageable pageable){

        QShare share = QShare.share;
        QShareImg shareImg = QShareImg.shareImg;

        //QShareRecipeDto @QueryProjection을 하용하면 DTO로 바로 조회 가능
        QueryResults<ShareRecipeDto> results = queryFactory.select(new QShareRecipeDto(share.id, share.foodNm,
                        shareImg.imgUrl,share.category, share.recipe, share.ingredients))
                // join 내부조인 .repImgYn.eq("Y") 대표이미지만 가져온다.
                .from(shareImg).join(shareImg.share,share).where(shareImg.repImgYn.eq("Y"))
                .where(share.category.eq("WESTERN_FOOD"))
                .orderBy(share.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        List<ShareRecipeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable,total);
    }
    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QShare.share.foodNm.like("%"+searchQuery+"%");
    }

}