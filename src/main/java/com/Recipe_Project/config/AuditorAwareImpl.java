package com.Recipe_Project.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


    public class AuditorAwareImpl implements AuditorAware<String> {
        // optional 클래스는 아래와 같은 value 에 값을 저장하기 때문에 값이 null 이더라도
        // 바로 NullPointError 가 발생하지 않으며,
        // 클래스이기 때문에 각종 메소드를 제공
        @Override
        public Optional<String> getCurrentAuditor(){
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String nickname = "";
            if(authentication != null){
                // 현재 로그인 한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정
                nickname = authentication.getName();
            }
            return Optional.of(nickname);
        }

}
