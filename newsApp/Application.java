package newsApp;

import newsApp.services.ArticleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import newsApp.dao.ArticleRetrieveDao;

@SpringBootApplication
public class Application {

    private final ArticleService articleService;
    private final ArticleRetrieveDao articleDAO;

    @Autowired
    public Application(ArticleService articleService, ArticleRetrieveDao articleDAO) {
        this.articleService = articleService;
        this.articleDAO = articleDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run() {
        System.out.println(articleService.getPostManAddress() + articleService.createHash());
    }
}


