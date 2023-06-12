package ecommerce.bookstore.entity;

public class CartItemRequest {

    private Long userId;
    private String bookId;

    public CartItemRequest() {
    }

    public CartItemRequest(String bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}