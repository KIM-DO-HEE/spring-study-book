package com.example.springstudybook.web;

import com.example.springstudybook.service.posts.PostsService;
import com.example.springstudybook.web.dto.PostsResponseDto;
import com.example.springstudybook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsSaveRequestDto requestDto){
//        return postsService.save(requestDto);
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/vi/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void delete(@PathVariable Long id){
         postsService.deleteById(id);
    }
}
