package com.example.springstudybook.web.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostsRepositoryTest {

//  Junit에서 단위테스트가 끝날때마다 수행되는 메소드를 지정
//  여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어
//  다음 테스트 실행 시 테스트가 실패할 수 있다
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

//    @Test
//    public void saveAndGetPost(){
//
//        //given
//        String title = "테스트 게시글";
//        String content = "테스트 본문";
//
//        postsRepository.save(Posts.builder()
//                .title(title)
//                .content(content)
//                .author("test@naver.com")
//                .build());
////      when
//        List<Posts> postsList = postsRepository.findAll();
//
//        //then
//        Posts posts = postsList.get(0);
//        System.out.println(posts.getTitle());
//        System.out.println(posts.getContent());
//        assertThat(posts.getTitle()).isEqualTo(title);
//
//        assertThat(posts.getContent()).isEqualTo(content);
//
//    }
}