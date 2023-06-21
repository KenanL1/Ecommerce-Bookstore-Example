package ecommerce.bookstore.integrationtest;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.service.BookService;
import ecommerce.bookstore.service.CartService;
import ecommerce.bookstore.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class CartAPITest {
    private User user;
    private Book book;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartService cartService;

    @BeforeAll
    public void setup() {

        // Check if user and book objects are already created
        // If not, create them and store them in the respective variables
        if (user == null) {
            // Create a new user
            user = userService.addUser("John", "john1", "password", "111 street", "NY", "US", "123456");
        }
        if (book == null) {
            // Create a new book
            book = bookService.insert("B001", "The Alchemist", 10.50, Category.FICTION, "Paulo Coelho", "https://www.example.com/alchemist");
        }
    }

    @Test
    @Order(1)
    public void testAddBookToCart() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": \"" + book.getBid() + "\", \"userId\": " + user.getId() + "}"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book added to the cart successfully."));
    }

    @Test
    public void testGetCart() throws Exception {
        long userId = user.getId();

//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/{userId}", userId));
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.user.id").value(userId));
        Cart cart = cartService.getCart(userId);
        assert (cart.getUser().getId() == userId);

    }

    @Test
    @Order(2)
    @Transactional
    public void testGetCartItemCount() throws Exception {
        long userId = user.getId();

        Book book1 = bookService.insert("B002", "The Alchemist", 10.50, Category.FICTION, "Paulo Coelho", "https://www.example.com/alchemist");
//        Book book2 = bookService.insert("B003", "The Alchemist", 10.50, Category.FICTION, "Paulo Coelho", "https://www.example.com/alchemist");

        // Add 2 books to cart
        cartService.addBookToCart(book1.getBid(), userId);
//        cartService.addBookToCart(book2.getBid(), userId);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/{userId}/cartCount", userId));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("2"));
    }

    @Test
    public void testRemoveBookFromCart() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cart/removeBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": \"" + book.getBid() + "\", \"userId\": " + user.getId() + "}"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book removed from the cart successfully."));
    }

    @Test
    public void testClearCart() throws Exception {
        long userId = user.getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cart/{userId}/clearCart", userId));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cart cleared successfully."));
    }
}