//package com.Recipe_Project.repository;
//
//import com.Recipe_Project.dto.WishDetailDto;
//import com.Recipe_Project.entity.WishRecipe;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface WishRecipeRepository extends JpaRepository<WishRecipe, Long> {
//
//    WishRecipe findByWishIdAndShareId(Long wishId, Long shareId);
//
//    @Query("select new com.shop.dto.WishDetailDto(wr.id, i.foodNm, im.imgUrl) " +
//            "from WishRecipe wr, ShareImg im " +
//            "join wr.wish i " +
//            "where wr.wish.id = :wishId " +
//            "and im.wish.id = ci.wish.id " +
//            "and im.repImgYn = 'Y' " +
//            "order by ci.regTime desc")
//
//    List<WishDetailDto> findWishDetailDtoList(Long wishId);
//}
