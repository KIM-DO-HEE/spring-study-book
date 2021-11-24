package com.example.springstudybook.web;

import com.example.springstudybook.config.auth.LoginUser;
import com.example.springstudybook.config.dto.SessionUser;
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

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        
//      CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구현
//      로그인 성공시 httpSession.getAttribute("user")에서 값을 가져올 수 있다
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

//        System.out.println("유저명"+user.getName());

        System.out.println("user"+user);
        
//      세션에 저장된 값이 있을 때만 model에 userName으로 등록한다
//      세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게된다
        if(user != null){
            model.addAttribute("test", user.getName());
        }
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
