package com.example.springstudybook.config.auth;

import com.example.springstudybook.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화
//WebSecurityConfigurerAdapter가 Adapter 패턴을 이용하나?
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserTypesOAuth2UserService customUserTypesOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//              h2-console 화면을 사용하기 위해 아래 2개의 옵션을 비활성화
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
//                  authorizeRequiests() :  URL별 권한 관리를 설정하는 옵션의 시작점, 해당 메소드가 있어야 andMatchers 옵션 사용 가능
                    .authorizeRequests()
//                  .andMatchers : 권한 관리 대상을 지정하는 옵션, URL, HTTP 메소드별로 관리가 가능,
//                  /* 등 지정된 URL들은 permitALL() 옵션을 통해 전체열람 권한을 준 것
                    .antMatchers("/", "/css/**",
                            "/images/**","/js/**","/h2-console/**").permitAll()
//                /api/vi/ 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 한것
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                 설정된 값들 이외 나머지 URL들을 나타낸것, 
//                 authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자(로그인한 사용자)들에게만 허용한다
                    .anyRequest().authenticated()
                .and()
//                  로그아웃 기능에 대한 여러 설정의 진입점
                    .logout()
//                  로그아웃 성공 시  / 주소로 이동한다
                        .logoutSuccessUrl("/")
                .and()
//                OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
//                OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                    .userInfoEndpoint()
//                소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
//                리소스서버(소셜서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있다
                    .userService(customUserTypesOAuth2UserService);

    }
}
