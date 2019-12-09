package com.finn.finnapi.controllers;

import java.io.IOException;
import java.util.List;
import com.finn.finnapi.models.Article;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:8081")
public class ArticleController extends Article {

    @RequestMapping("/index")
    public List<Article> index() {
        return Article.getAll();
    }

    @RequestMapping("/show/{id}")
    public Article show(@PathVariable("id") String id) {
        return Article.getOne(id);
    }

    @RequestMapping("/srap/{numberOfPagesToScrap}")
    public String updateArticles(@PathVariable("numberOfPagesToScrap") String numberOfPagesToScrap) {
        try {
            ScrapController.updateDB(new Integer(numberOfPagesToScrap)).stream().forEach(article -> {
                Article.update(article);
            });
        } catch (NumberFormatException | IOException e) {
            System.out.println(e);
        }
        return "{ \"Message\": \"Successful scraping\"}";
    }

    @RequestMapping("/destroy/{id}")
    public String delete(@PathVariable("id") String id) {
        Article.destroy(id);
        return "{ \"Message\": \"Deleted Article Succeccfully\"}";
    }
}
