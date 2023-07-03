package ecommerce.bookstore.entity;

public class CartItemRequest {

    private Long userId;
    private String bookId;
    private int quantity;

    public CartItemRequest() {
    }

    public CartItemRequest(String bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public CartItemRequest(Long userId, String bookId, int quantity) {
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}