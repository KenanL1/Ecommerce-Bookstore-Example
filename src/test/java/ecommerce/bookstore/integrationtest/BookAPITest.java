package ecommerce.bookstore.integrationtest;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.restapi.BookAPI;
import ecommerce.bookstore.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class BookAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookAPI bookAPI;

    private Book book;

    @BeforeAll
    public void setUp() {
        String bid = "B001";
        String title = "The Alchemist";
        Double price = 10.50;
        Category category = Category.FICTION;
        String author = "Paulo Coelho";
        String pictureLink = "https://www.example.com/alchemist.jpg";

        book = bookService.insert(bid, title, price, category, author, pictureLink);
    }

    @Test
    void testGetBooksByCategory() throws Exception {
        String category = "FICTION";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book")
                        .param("category", "FICTION"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].category").value(book.getCategory().toString()));
    }

    @Test
    void testGetBooksInvalidCategory() throws Exception {
        String category = "INVALID";
        String title = null;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book")
                        .param("category", category)
                        .param("title", title))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid category"));
    }

    void testGetBooksByTitle() throws Exception {
        String title = "Alchemist";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book")
                        .param("title", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bid").value(book.getBid()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(book.getCategory().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].picture_link").value(book.getPicture_link()));
    }

    @Test
    void testGetAllBooks() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap());

    }

    @Test
    void testRetrieveBook() throws Exception {
        String bid = book.getBid();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/{bid}", bid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bid").value(book.getBid()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(book.getCategory().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.picture_link").value(book.getPicture_link()));
    }

    @Test
    @Order(1)
    void testBookAdd() throws Exception {
        String bid = "B002";
        String title = "The Alchemist";
        double price = 10.50;
        Category category = Category.FICTION;
        String author = "Paulo Coelho";
        String pictureLink = "https://www.example.com/alchemist.jpg";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .param("bid", bid)
                        .param("title", title)
                        .param("price", String.valueOf(price))
                        .param("category", category.toString())
                        .param("author", author)
                        .param("pictureLink", pictureLink))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book added from database"));

    }

    @Test
    @Order(2)
    void testBookDelete() throws Exception {
        String bid = "B002";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/book/{bid}", bid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book removed from database"));
    }
}