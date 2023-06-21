package ecommerce.bookstore.unittest;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.CartItemRequest;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.restapi.CartAPI;
import ecommerce.bookstore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartAPITest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartAPI cartAPI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookToCart() {
        CartItemRequest request = new CartItemRequest();
        request.setBookId("1");
        request.setUserId(1L);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(getSuccessResponse("Book added to the cart successfully."));

//        when(cartService.addBookToCart("1", 1L)).thenReturn(true);

        ResponseEntity<?> actualResponse = cartAPI.addBookToCart(request);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartService, times(1)).addBookToCart("1", 1L);
    }

    @Test
    public void testGetCart() {
        long userId = 1L;
        Cart expectedCart = new Cart();
        User user = new User();
        user.setId(userId);
        expectedCart.setUser(user);

        when(cartService.getCart(userId)).thenReturn(expectedCart);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedCart);
        ResponseEntity<?> actualResponse = cartAPI.getCart(userId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    public void testGetCartItemCount() {
        long userId = 1L;

        Book book1 = new Book("1", "Book 1", 10.1, Category.CHILDREN, "Author 1", "http://testsite");
        Book book2 = new Book("2", "Book 2", 10.2, Category.CHILDREN, "Author 2", "http://testsite");

        List<Book> cartList = new ArrayList<>();
        cartList.add(book1);
        cartList.add(book2);

        Cart cart = new Cart();
        User user = new User();
        user.setId(userId);
        cart.setUser(user);
        cart.setCartList(cartList);

        when(cartService.getCart(userId)).thenReturn(cart);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(2);
        ResponseEntity<?> actualResponse = cartAPI.getCartItemCount(userId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    public void testRemoveBookFromCart() {
        CartItemRequest request = new CartItemRequest();
        request.setBookId("1");
        request.setUserId(1L);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(getSuccessResponse("Book removed from the cart successfully."));

//        when(cartService.removeBookFromCart(1L, 1L)).thenReturn(true);

        ResponseEntity<?> actualResponse = cartAPI.removeBookFromCart(request);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartService, times(1)).removeBookFromCart(1L, "1");
    }

    @Test
    public void testClearCart() {
        long userId = 1L;

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(getSuccessResponse("Cart cleared successfully."));

        ResponseEntity<?> actualResponse = cartAPI.clearCart(userId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartService, times(1)).clearCart(userId);
    }

    private Map<String, Object> getSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}