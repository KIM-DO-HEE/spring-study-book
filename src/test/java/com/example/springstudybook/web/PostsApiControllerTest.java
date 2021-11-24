package com.example.springstudybook.web;

import com.example.springstudybook.domain.posts.Posts;
import com.example.springstudybook.domain.posts.PostsRepository;
import com.example.springstudybook.web.dto.PostsSaveRequestDto;
import com.example.springstudybook.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
//  인증된 모의(가짜)사용자를 만들어서 사용, roles에 권한을 추가할 수 있다
//  이 어노테이션은 ROLE_USER 권한을 가진 사용자가 API를 요청하는것과 동일한 효과
//   But, @MockMvc에서만 작동하기 때문에 MockMvc를 import 해야한다
    @WithMockUser(roles = "USER")
    public void insertPost() throws Exception{
//        given
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

//        when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class)
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    //        then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        System.out.println(all.get(0).getTitle());
        System.out.println(all.get(0).getContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void modify_post() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

//      when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

//      then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void BasetTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2021,11,02,0,0,0);
        postsRepository.save(Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>> createDate="+posts.getCreatedDate()+",modifiedDate="+posts.getModifiedDate());
    }

}