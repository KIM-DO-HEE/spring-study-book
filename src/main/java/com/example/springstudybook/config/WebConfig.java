package com.example.springstudybook.config;

// LoginUsrArgumentResolver를 스프링에서 인식할 수 있도록 WebMvcConfigurer에 추가함

import com.example.springstudybook.config.auth.LoginUser;
import com.example.springstudybook.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private  final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}
