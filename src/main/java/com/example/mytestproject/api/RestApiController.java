package com.example.mytestproject.api;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class RestApiController {

    private static final Logger log = LogManager.getLogger(RestApiController.class);

    @Autowired
    private ArticleService articleService;
//
//
    @GetMapping("/api/allArticles")
    public List<Article> AllArticles(){
        //데이터 리턴 -> 어떤? 게시물이 단수? 복수? ->  복수 : List<>
        //데이터는 어디? : DB안에
        //DB에 접근은 어떻게 ? : Repo 초기화
        return articleService.findAll();
    }
//
    @GetMapping("/api/articles/{id}")
    public Article articles(@PathVariable Long id){
        return articleService.articles(id);
    }
//
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){  //dto를 받는데 body만 받는다.
        Article serviceRes = articleService.create(dto);
        //생성이 성공이었을 때 / 생성이 실패했을 때
        //삼항연산자
        return (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).body(serviceRes) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable Long id,
                                         @RequestBody ArticleForm dto) {

        Article patchData = articleService.patch(id, dto);

        return (patchData != null) ?
                ResponseEntity.status(HttpStatus.OK).body(patchData) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        Article serviceRes = articleService.delete(id);

        return (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(
            @RequestBody List<ArticleForm> dtos
    ){
        List<Article> createList =
                articleService.createTransaction(dtos);

        return (createList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //    GET  - /articles/articleId/comments
    //    post - /articles/articleId/comments
    //    patch - /comments/id
    //    delete - /comments/id

}
