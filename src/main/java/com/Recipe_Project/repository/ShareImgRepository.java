package com.Recipe_Project.repository;

import com.Recipe_Project.entity.ShareImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareImgRepository extends JpaRepository<ShareImg,Long> {

    List<ShareImg> findByShareIdOrderByIdAsc(Long shareId);

    ShareImg findByShareIdAndRepImgYn(Long shareId, String repImgYn);
}

