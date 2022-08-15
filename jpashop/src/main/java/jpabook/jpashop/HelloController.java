package jpabook.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Controller
public class HelloController {

    @PostConstruct
    public void init(){
        System.out.println("init.....");
    }

    @PreDestroy
    public void exit(){
        System.out.println("exit.....");
    }

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello");
        return "hello";
    }
}
