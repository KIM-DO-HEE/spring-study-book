package com.example.springstudybook.web;

import com.example.springstudybook.service.posts.PostsService;
import com.example.springstudybook.web.dto.PostsListResponseDto;
import com.example.springstudybook.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";  //앞의 경로는 src/main/template, 뒤의 경로는 mustache로 지정된다
//       즉 여기서 src/main/templaate/index.mustache로 반환되어 ViewResolver가 처리하게된다
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";

    }

}
