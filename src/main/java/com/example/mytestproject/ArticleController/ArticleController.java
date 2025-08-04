package com.example.mytestproject.ArticleController;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // simple logging facade
public class ArticleController {
    private static final Logger log = LogManager.getLogger(ArticleController.class);  // articles/new.mustache를 보여주는 컨트롤러

    //의존성 주입 = DI(Denpendency Injection)
    @Autowired // 이미 생성한 repo 객체를 DI(인터페이스 구현했다고 침)
    private ArticleRepository articleRepository; //entity받아서 db에 넣어줌

    @Autowired
    private CommentService commentService;


    @GetMapping("/articles/new") // url주소로 요청
    public String newArticleForm(){
        return "articles/new"; // articles 디렉토리의 new라는 뷰 머스테치
    }

    // new.mustache에서 써놓은 내용 보내기
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString());

        // DTO => Entity 변환
        Article article = form.toEntity(); // 실행하면 변환이 된다고 가정 alt enter
        log.info(article.toString());
        //System.out.println(article.toString());

        //Repo로 Entitiy DB저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());



        return "redirect:/articles/" + saved.getId();
    }


    @GetMapping("/articles/{id}") //url로 단일 id조회
    public String articleId(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1번 id를 DB에 조회
       Article articleEntity = articleRepository.findById(id).orElse(null);
       List<CommentDto> commentDtos = commentService.comments(id);



        // 2번 - 모델에 데이터를 등록
        model.addAttribute("data", articleEntity);
        model.addAttribute("commentDtos", commentDtos);


        // 3번 - 사용자에게 화면 전달
        return "articles/articleId";
    }

    @GetMapping("/articles/index") //전체 조회
    public String index(Model model){

        // 1번 DB에 모든 게시글 데이터 가져오기
        // Iterable<Article> articleList = articleRepository.findAll(); // 업캐스팅
        // List<Article> articleList2 = (List<Article>)articleRepository.findAll(); //다운캐스팅
        // 1번 케스팅 (형변환)
        List<Article> articleList = articleRepository.findAll();

        // 2번 등록하기
        model.addAttribute("articleList", articleList);
        // 3번 화연 리턴

        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article article = articleRepository.findById(id).orElse(null);

        //데이터 등록
        model.addAttribute("article", article);


        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){ //신규데이터 받아옴
        log.info(form.toString());

        //1번 DTO를 Entity 변환하기
        Article entity = form.toEntity(); //받아온거를 entity로 변환
        log.info(entity.toString());

        //2번 Entity를 DB에 저장
        //2-1 수정하기 전 데이터를 불러오기
        //DB에서 데이터를찾고
        Article dbData = articleRepository.findById(form.getId()).orElse(null);

        //2-2
        //갱신 데이터를 저장하고
        //데이터가 없는 경우 처리 (있으면 저장)
        if(dbData != null){
            Article saved = articleRepository.save(entity);  //form데이터로 넘겨온 신규데이터를 entity로 만든거를 저장해줌
            log.info(saved.toString());
        }

        //3번 수정 결과 화면 전환
        return "redirect:/articles/" + entity.getId();
    }


    // HTTP요청 시 메서드 = GET, POST, UPDATE, DELETE
    // HTML요청 시 메서드 = GET, POST
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청 확인");

        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());

        if(article != null){
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다");
        }

        return "redirect:/articles/index";
    }


}
