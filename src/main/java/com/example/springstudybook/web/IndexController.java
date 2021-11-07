package com.example.springstudybook.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";  //앞의 경로는 src/main/template, 뒤의 경로는 mustache로 지정된다
//       즉 여기서 src/main/templaate/index.mustache로 반환되어 ViewResolver가 처리하게된다
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }


}
