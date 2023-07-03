package ecommerce.bookstore.entity;

import jakarta.persistence.*;

@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "bid")
    private Book book;

    private int quantity;

    public CartItems() {
    }

    public CartItems(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    @Transient
    public Double getTotalPrice() {
        return getBook().getPrice() * getQuantity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
