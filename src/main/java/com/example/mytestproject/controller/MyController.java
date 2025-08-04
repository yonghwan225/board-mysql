package com.example.mytestproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러 선언
public class MyController {

    //메서드 선언
    @GetMapping("/hello")  //http://localhost:8080/hello <이 부분
    public String helloMethod(Model model){
        model.addAttribute("username","용환아"); //키와 값으로 이루어짐
        return "greeting";  //mustache의 이름을 리턴에 넣으면 "뷰"에게 전달되는 효과
    }

    //메서드 선언
    @GetMapping("/bye")
    public String byeMethod(Model model){
        model.addAttribute("username","용환아");
        return "bye";  //mustache의 이름을 리턴에 넣으면 뷰에게 전달되는 효과
    }

    @GetMapping("/random-sentence")
    public String randomSentence(Model model) {
        String[] quotes = {
                "오늘의 햇살은 마치 레몬즙을 뿌린 듯 상큼해.",
                "고양이가 피아노를 치면서 노래를 불렀다.",
                "초코칩 쿠키는 달빛 아래서 더 맛있게 느껴진다.",
                "바나나는 바람을 타고 바다를 건넌다.",
                "우주 속에서 사과는 더 빨갛게 빛난다.",
                "비 오는 날에는 파란 개구리가 춤을 춘다.",
                "모래성은 꿈속에서만 무너진다.",
                "무지개 색깔의 나비가 내 어깨에 앉았다.",
                "달팽이 경주에서 꼴찌가 제일 빠르다.",
                "별똥별은 소원을 들어주는 우체부야.",
        };
        int randInt = (int) (Math.random() * quotes.length);
        model.addAttribute("randomSentence", quotes[randInt]);
        return "random_sentence";  //mustache 이름
    }
}
