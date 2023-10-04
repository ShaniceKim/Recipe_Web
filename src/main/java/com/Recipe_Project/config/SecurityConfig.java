package com.Recipe_Project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration // 설정
@EnableWebSecurity
//WebSecurityConfigurerAdapter 상속 받는 클래스에 @EnableWebSecurity 선언을 하면
//SpringSecurityFilterChain 이 자동 포함 메소드를 오버라이딩 할 수 있습니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .formLogin()
                .loginPage("/members/login") //로그인 페이지 링크
                .defaultSuccessUrl("/") //로그인 성공 후 리다이렉트 주소
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and() // 로그인이랑 로그아웃은 필드가 다르기 때문에 둘을 and 로 묶어 연결해준다 <http security>
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 성공 시 리다이렉트 주소
                //.invalidateHttpSession(true) // 세션 날리기 (추가한거임 안되면 빼) -> 로그아웃 이후 세션 전체 삭제 여부
                .logoutSuccessUrl("/");

        http
                .authorizeRequests() //특정한 경로에 특정한 권한을 가진 사용자만 접근할 수 있도록 설정
                .mvcMatchers("/","/members/**","/images/**","/browse/**").permitAll() //누구나 접근 허용
                .mvcMatchers("/admin/**","/share/**","/mypage/**").hasRole("USER") //사용자만 접근 가능
                .anyRequest().authenticated();//나머지 요청들은 권한의 종류에 상관없이 권한이 있어야 접근가능

        http
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());


    }

    @Bean //원두 -> 객체 빈객체 -> SpringContainer 들어갑니다. 이객체 하나로 돌려쓴다.(싱글톤)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 비밀번호를 암호화 하는 해시함수
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**");
    }


}
