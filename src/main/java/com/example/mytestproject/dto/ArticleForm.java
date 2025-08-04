package com.example.mytestproject.dto;

import com.example.mytestproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 권한 반환값 함수명 (파라미터)
// 생성자 만들기: 클래스 우클릭 하고  generate > constructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {

    private Long id;

    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, title, content);
    }

    public Long getId() {
        return id;
    }
}