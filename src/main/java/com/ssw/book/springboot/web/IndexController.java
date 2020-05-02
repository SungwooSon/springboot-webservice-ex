package com.ssw.book.springboot.web;

import com.ssw.book.springboot.service.posts.PostsService;
import com.ssw.book.springboot.web.dto.PostsResponseDto;
import config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /**
     * 문자열 return시 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다.
     * 여기선 src/main/resources/templates/index.mustache 로 전환되어, View Resolver가 처리함.
     */
    /**
     * Model
     * 서버 템플릿 엔진에ㅓ 사용할 수 있는 객체를 저장.
     * postsService.findAllDesc() 결과로 가져온 결과(Posts)를 posts로 index.mustache에 전달.
     *
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        System.out.println("user : " + user);
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
