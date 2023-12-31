package ecommerce.bookstore.unittest;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.restapi.BookAPI;
import ecommerce.bookstore.service.BookService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookAPITest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookAPI bookAPI;

    private Book book;
    private List<Book> bookList;
    private Page<Book> mockedPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setBid("B001");
        book.setTitle("The Alchemist");
        book.setPrice(10.50);
        book.setCategory(Category.FICTION);
        book.setAuthor("Paulo Coelho");
        book.setPicture_link("https://www.example.com/alchemist.jpg");

        bookList = new ArrayList<>();
        bookList.add(book);

        // Create a mock Page<Book> object with the reviews
        mockedPage = new PageImpl<>(bookList);
    }

    @Test
    void testGetBooks() {
        String category = "FICTION";
        String title = null;

        when(bookService.getBookByCategory(Category.FICTION, 0, 10, "desc", "bid"))
                .thenReturn(mockedPage);

        ResponseEntity<?> response = bookAPI.getBooks(category, title, 0, 10, "desc", "bid");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedPage, response.getBody());
    }

    @Test
    void testGetBooksInvalidCategory() {
        String category = "INVALID";
        String title = null;

        when(bookService.getBookByCategory(null, 0, 10, "desc", "bid"))
                .thenThrow(new IllegalArgumentException("Invalid category"));

        ResponseEntity<?> response = bookAPI.getBooks(category, title, 0, 10, "desc", "bid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid category", response.getBody());
    }

    @Test
    void testGetBooksByTitle() {
        String category = null;
        String title = "Alchemist";

        when(bookService.getBooksByTitle(title, 0, 10, "desc", "bid"))
                .thenReturn(mockedPage);

        ResponseEntity<?> response = bookAPI.getBooks(category, title, 0, 10, "desc", "bid");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedPage, response.getBody());
    }

    @Test
    void testGetAllBooks() {
        String category = null;
        String title = null;

        when(bookService.getAllBooks(0, 10, "desc", "bid"))
                .thenReturn(mockedPage);

        ResponseEntity<?> response = bookAPI.getBooks(category, title, 0, 10, "desc", "bid");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedPage, response.getBody());
    }

    @Test
    void testRetrieveBook() throws Exception {
        String bid = "B001";

        when(bookService.getByBid(bid)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookAPI.retrieveBook(bid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    @Order(1)
    void testBookAdd() {
        String bid = "B001";
        String title = "The Alchemist";
        double price = 10.50;
        Category category = Category.FICTION;
        String author = "Paulo Coelho";
        String pictureLink = "https://www.example.com/alchemist.jpg";

        bookService.insert(bid, title, price, category, author, pictureLink);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Book added from database");

        ResponseEntity<?> response = bookAPI.bookAdd(bid, title, price, category, author, pictureLink);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testBookDelete() {
        String bid = "B001";

        doNothing().when(bookService).deleteBook(bid);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Book removed from database");

        ResponseEntity<Map<String, Object>> response = bookAPI.bookDelete(bid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}