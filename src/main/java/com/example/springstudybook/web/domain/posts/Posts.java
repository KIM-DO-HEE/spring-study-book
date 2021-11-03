package com.example.springstudybook.web.domain.posts;

import com.example.springstudybook.web.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
// junit5 변경 후 코드 변경 : 참고자료 : https://jojoldu.tistory.com/539?category=717427
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity                                             {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }




}
