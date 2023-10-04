//package com.Recipe_Project.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "wish_recipe")
//public class WishRecipe extends BaseEntity {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "wish_recipe_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "wish_id")
//    private Wish wish;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="share_id")
//    private Share share;
//
//    public static WishRecipe createWishRecipe(Wish wish, Share share){
//        WishRecipe wishRecipe = new WishRecipe();
//        wishRecipe.setWish(wish);
//        wishRecipe.setShare(share);
//        return wishRecipe;
//    }
//}
