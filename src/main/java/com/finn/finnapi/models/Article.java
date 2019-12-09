package com.finn.finnapi.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Article extends DBController {
    private Integer id;
    private String title;
    private Double price;
    private String link;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Double getPrice() {
        return price;
    }

    public static List<Article> getAll() {
        List<Article> articles = new ArrayList<Article>();
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM article";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("articleID"));
                article.setTitle(rs.getString("title"));
                article.setPrice(rs.getDouble("price"));
                article.setLink(rs.getString("link"));
                articles.add(article);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static Article getOne(String id) {
        Article article = new Article();
        try (Connection connection = connect()) {

            String sql = "SELECT * FROM article WHERE articleID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                article.setId(rs.getInt("articleID"));
                article.setTitle(rs.getString("title"));
                article.setPrice(rs.getDouble("price"));
                article.setLink(rs.getString("link"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    public static void update(Article art) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO article (title, price, link) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, art.getTitle());
            pstmt.setDouble(2, art.getPrice());
            pstmt.setString(3, art.getLink());
            pstmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void destroy(String id) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM article WHERE articleID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
