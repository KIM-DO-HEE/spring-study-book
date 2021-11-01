package com.example.springstudybook.service.posts;

import com.example.springstudybook.web.domain.posts.Posts;
import com.example.springstudybook.web.domain.posts.PostsRepository;
import com.example.springstudybook.web.dto.PostsResponseDto;
import com.example.springstudybook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다 id="+ id ));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){

        Posts posts = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(("해당 게시글이 없습니다 id ="+ id)));
        return new PostsResponseDto(posts);
    }
}