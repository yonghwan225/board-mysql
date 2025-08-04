package com.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // 테이블
@AllArgsConstructor //생성자
@ToString
@NoArgsConstructor //기본생성자 대신
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //DB에게 id중복 안되도록 자동생성 요청
    private Long id;

    //Article(){}//기본 생성자

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}

