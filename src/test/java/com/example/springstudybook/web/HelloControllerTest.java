package com.example.springstudybook.web;

import com.example.springstudybook.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = HelloController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;


    @WithMockUser(roles = "USER")
    @Test
    public void hello () throws  Exception{
        String hello= "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto() throws  Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //jsonPath : json 응답값을 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount))); // $를 기준으로 필드명을 명시시
    }
}
