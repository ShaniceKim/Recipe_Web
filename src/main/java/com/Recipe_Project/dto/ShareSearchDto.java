package com.Recipe_Project.dto;


import com.Recipe_Project.constant.ShareStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareSearchDto {

    private String searchDateType;

    private ShareStatus searchShareStatus;

    private String searchBy;

    private String searchQuery = "";
}
