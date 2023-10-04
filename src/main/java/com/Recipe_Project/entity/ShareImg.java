package com.Recipe_Project.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "share_img")
@Getter
@Setter
public class ShareImg extends BaseEntity {
    @Id
    @Column(name = "share_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;// 이미지파일명 dog.png

    private String oriImgName;// 원본이미지 파일명

    private String imgUrl;// 이미지 경로

    private String repImgYn;// 대표이미지여부 첫번째 이미지 Y  그 이외 N

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "share_id")
    private Share share;

    public void updateShareImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public void deleteShareImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}