package ecommerce.bookstore.integrationtest;

import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.service.BookService;
import ecommerce.bookstore.service.ReviewService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ReviewAPITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;

    @BeforeAll
    public void setUp() {
        String bid = "B001";
        String title = "The Alchemist";
        Double price = 10.50;
        Category category = Category.FICTION;
        String author = "Paulo Coelho";
        String pictureLink = "https://www.example.com/alchemist.jpg";

        bookService.insert(bid, title, price, category, author, pictureLink);
    }

    @Test
    void testAddReview() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/review")
                        .content("{\"bid\":\"B001\",\"review\":\"Good book\",\"rating\":4}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bid").value("B001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.review").value("Good book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(4));
    }

    @Test
    void testGetReviews() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/review/book/{bid}", "B001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bid").value("B001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review").value("Good book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(4));
    }

    @Test
    void testDeleteReview() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/review/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void testDeleteAllReviewByBook() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/review/book/{bid}", "B001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}