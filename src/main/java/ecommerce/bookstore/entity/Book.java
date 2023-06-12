package ecommerce.bookstore.entity;

import ecommerce.bookstore.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    private String bid;
    private String title;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String author;
    private String picture_link;

    public Book() {
    }

    public Book(String bid, String title, double price, Category category, String author, String picture_link) {
        this.bid = bid;
        this.title = title;
        this.price = price;
        this.category = category;
        this.author = author;
        this.picture_link = picture_link;
    }

    //returns bid, title, price, category and author of the book in a string.
    public String getInfo() {
        String info = bid + ", " + title + ", $" + price + ", " + category + ", " + author;
        return info;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAuthor() {
        if (author == null)
            return "";
        else
            return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPicture_link() {
        if (picture_link == null)
            return "";
        else
            return picture_link;
    }

    public void setPicture_link(String picture_link) {
        this.picture_link = picture_link;
    }
}
