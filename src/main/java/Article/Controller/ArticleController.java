package Article.Controller;

import Article.Model.Article;
import Article.Model.ArticleFunction;
import Article.Model.Commend;
import Article.Model.CommendFuction;
import Article.View.ArticleView;
import Article.View.CommendView;

import java.util.ArrayList;
import java.util.Scanner;

public class ArticleController {
    ArticleFunction articleFunction = new ArticleFunction();
    ArticleView articleView = new ArticleView();
    CommendView commendView = new CommendView();
    CommendController commendController = new CommendController();
    CommendFuction commendFuction = new CommendFuction();
    Scanner scanner = new Scanner(System.in);
    ArrayList<Article> articles = new ArrayList<>();
    public void add(){
        System.out.print("제목 : ");
        String title = scanner.nextLine();
        System.out.print("내용 : ");
        String content = scanner.nextLine();

        articleFunction.insert(title, content);
        System.out.println("게시글 추가 되었습니다.");
    }
    public void list(){
        ArrayList<Article> articles = articleFunction.findArticlelist();
        articleView.Printlist(articles);
    }
    public void update(){
        list();
        System.out.print("수정할 게시글 번호 : ");
        int num = getParamId(scanner.nextLine(), -1);
        if(num == -1){
            return;
        }
        Article article = articleFunction.findById(num);

        if(article == null){
            System.out.println("게시글이 없습니다.");
        }else{
            System.out.print("수정할 제목 : ");
            String newTitle = scanner.nextLine();
            System.out.print("수정할 내용 : ");
            String newContent = scanner.nextLine();

            article.setTitle(newTitle);
            article.setContent(newContent);
        }
    }
    public void delete() {
        list();
        System.out.print("삭제할 게시글 번호 : ");
        int num = getParamId(scanner.nextLine(), -1);
        if (num == -1) {
            return;
        }
        Article article = articleFunction.findById(num);

        if (article == null) {
            System.out.println("게시글이 없습니다.");
        }else{
            articleFunction.delete(article);
            System.out.println("삭제 완료");
        }
    }
    public void detail(){
        list();
        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
        int num = getParamId(scanner.nextLine(), -1);
        if (num == -1) {
            return;
        }
        Article article = articleFunction.findById(num);

        if (article == null) {
            System.out.println("게시글이 없습니다.");
        }else{
            article.setView_count(article.getView_count() + 1);
            articleView.PrintAlllist(article);
            Outter:
            while (true) {
                System.out.print("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 추천, 3. 수정, 4. 삭제, 5. 목록으로) : ");
                int index = Integer.parseInt(scanner.nextLine());
                switch (index) {
                    case 1:
                        System.out.println("댓글 등록");
                        commendController.commendAdd();
                        break;
                    case 2:
                        System.out.println("추천");
                        break;
                    case 3:
                        System.out.println("수정");
                        break;
                    case 4:
                        System.out.println("삭제");
                        break;
                    case 5:
                        System.out.println("목록으로");
                        break Outter;
                    default:
                        System.out.println("잘못입력하셨습니다.");
                        break;
                }
            }
        }
    }
    public void search(){
        System.out.print("검색할 키워드를 입력하세요 : ");
        String keyword = scanner.nextLine();

        ArrayList<Article> articles = articleFunction.findByTitle(keyword);
        articleView.Printlist(articles);
    }
    public int getParamId(String input, int defaultvalue){
        try{
            int num = Integer.parseInt(input);
            return num;
        }catch (Exception e){
            System.out.println("숫자를 입력해 주세요");
        }
        return defaultvalue;
    }
}
