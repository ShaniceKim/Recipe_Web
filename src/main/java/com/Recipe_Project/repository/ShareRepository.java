package com.Recipe_Project.repository;


import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long>,
        QuerydslPredicateExecutor<Share>, ShareRepositoryCustom {

    List<Share> findByFoodNm(String foodNm);

    Optional<Share> findById(Long id);





}
