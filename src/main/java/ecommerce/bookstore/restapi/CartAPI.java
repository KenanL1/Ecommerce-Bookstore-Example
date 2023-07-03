package ecommerce.bookstore.restapi;

import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.CartItemRequest;
import ecommerce.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/cart")
public class CartAPI {

    @Autowired
    private CartService cartService;

    // Add a book to the cart
    @PostMapping("/addBook")
    public ResponseEntity<?> addBookToCart(@RequestBody CartItemRequest request) {
        cartService.addBookToCart(request.getBookId(), request.getUserId());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book added to the cart successfully.");
        return ResponseEntity.ok(response);
    }

    // Get cart
    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable("userId") long userId) {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    // Update Item quantity
    @PostMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody CartItemRequest request) {
        cartService.updateBookQuantity(request.getBookId(), request.getUserId(), request.getQuantity());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Quantity updated");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/{bookId}")
    public ResponseEntity<?> checkBookInCart(@PathVariable("userId") Long userId, @PathVariable("bookId") String bookId) {
        boolean bookInCart = cartService.checkBookInCart(bookId, userId);
        return ResponseEntity.ok(bookInCart);
    }

    // Get number of books in cart
    @GetMapping("/{userId}/cartCount")
    public ResponseEntity<?> getCartItemCount(@PathVariable("userId") long userId) {
        int count = cartService.getCart(userId).getCartList().size();
        return ResponseEntity.ok(count);
    }

    // Remove item from cart
    @DeleteMapping("/removeBook")
    public ResponseEntity<?> removeBookFromCart(@RequestBody CartItemRequest request) {
        cartService.removeBookFromCart(request.getUserId(), request.getBookId());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book removed from the cart successfully.");
        return ResponseEntity.ok(response);
    }

    // Clear the cart
    @DeleteMapping("/{userId}/clearCart")
    public ResponseEntity<?> clearCart(@PathVariable("userId") Long userId) {
        cartService.clearCart(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cart cleared successfully.");
        return ResponseEntity.ok(response);
    }
}
