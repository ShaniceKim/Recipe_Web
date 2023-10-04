package com.Recipe_Project.entity;
import com.Recipe_Project.constant.ShareStatus;
import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.dto.ShareFormDto;
import com.Recipe_Project.dto.ShareImgDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "share")
@Getter
@Setter
@ToString
public class Share extends BaseEntity {


    @Id // 기본키
    @Column(name="share_id")
    @GeneratedValue(strategy = GenerationType.AUTO) //  자동을 1씩 증가
    private Long id; //pk 값

    @Column(nullable = false, length = 20)
    private String foodNm; // 음식이름

    @Lob
    @Column(nullable = false)
    private String ingredients; // 재료들

    @Lob
    @Column(nullable = false)
    private String recipe; // 레시피

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate; // 등록 시간
    private LocalDateTime modifiedDate; // 수정 시간

    private String category;


    @OneToMany(mappedBy = "share",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShareImg> shareImgs = new ArrayList<ShareImg>();


    //insertable: 엔티티 저장시 이 필드도 같이 저장. false 로 설정하면 데이터베이스에 저장하지 않는다. 읽기전용 일 때 사용한다.
    //updatable: 위와 동일한 하지만 수정일 때 해당된다.

    public void updateShare(ShareFormDto shareFormDto){ //수정 (업데이트)
        this.foodNm = shareFormDto.getFoodNm(); //음식이름
        this.ingredients = shareFormDto.getIngredients(); //음식재료
        this.recipe = shareFormDto.getRecipe(); //음식레시피
        this.category = shareFormDto.getCategory(); //음식카테고리

    }

}

