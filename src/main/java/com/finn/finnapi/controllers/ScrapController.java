package com.finn.finnapi.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.finn.finnapi.config.Config;
import com.finn.finnapi.models.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapController extends Article {
  public static boolean stringContainsItemFromList(String str, List<String> items) {
    return items.stream().parallel().anyMatch(str::contains);
  }

  public static List<Article> updateDB(Integer numberOfPagesToScrap) throws IOException {
    Integer pageNumber = 1;
    List<Article> articles = new ArrayList<Article>();
    while (pageNumber <= numberOfPagesToScrap) {
      String url = String.format(
          "https://finn.no/bap/forsale/search.html?page=%s&q=objektiv&search_type=SEARCH_ID_BAP_ALL", pageNumber);
      Document doc = Jsoup.connect(url).get();
      List<String> filteredTitles = Article.getAll().stream().map(article -> article.getTitle().toLowerCase())
          .collect(Collectors.toList());
      doc.select(".ads__unit").stream().filter(article -> !article.select(".ads__unit__link").text().isEmpty()
          && !stringContainsItemFromList(article.select(".ads__unit__link").text().toLowerCase(), Config.getExclude())
          && stringContainsItemFromList(article.select(".ads__unit__link").text().toLowerCase(), Config.getInclude())
          && !stringContainsItemFromList(article.select(".ads__unit__link").text().toLowerCase(), filteredTitles))
          .forEach(filteredArticle -> {
            Article article = new Article();
            article.setTitle(filteredArticle.select(".ads__unit__link").text());
            article
                .setPrice(
                    Double
                        .parseDouble(
                            !filteredArticle.select(".ads__unit__img__ratio__price").text().isEmpty()
                                ? filteredArticle.select(".ads__unit__img__ratio__price").text().replaceAll(" ", "")
                                    .replaceFirst("kr", "")
                                : "0.0"));
            article.setLink(String.format("https://finn.no/bap/forsale/ad.html?finnkode=%s",
                filteredArticle.select(".ads__unit__link").attr("data-finnkode")));
            articles.add(article);
          });
      System.out.println(url);
      pageNumber++;
    }
    return articles;
  }
}
