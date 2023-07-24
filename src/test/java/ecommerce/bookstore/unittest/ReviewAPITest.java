package ecommerce.bookstore.unittest;

import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.restapi.ReviewAPI;
import ecommerce.bookstore.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewAPITest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewAPI reviewAPI;

    private Review review;
    private List<Review> reviewList;
    private Page<Review> mockedPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new Review();
        review.setBid("B001");
        review.setReview("Good book");
        review.setRating(4);
        reviewList = new ArrayList<>();
        reviewList.add(review);

        // Create a mock Page<Book> object with the reviews
        mockedPage = new PageImpl<>(reviewList);
    }

    @Test
    void testAddReview() {
        when(reviewService.insert(review.getBid(), review.getReview(), review.getRating())).thenReturn(review);
        ResponseEntity<?> response = reviewAPI.addReview(review);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    void testGetReviews() {
        String bid = "B001";

        when(reviewService.getReviewsByBid(bid, 0, 10, "desc", "id")).thenReturn(mockedPage);
        ResponseEntity<List<Review>> response = reviewAPI.getReviews(bid, 0, 10, "desc", "id");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewList, response.getBody());
    }

    @Test
    void testDeleteReview() {
        Long id = 1L;
        doNothing().when(reviewService).delete(id);
        reviewAPI.deleteReview(id);
        verify(reviewService, times(1)).delete(id);
    }

    @Test
    void testDeleteAllReviewByBook() {
        String bId = "B001";
        doNothing().when(reviewService).deleteAllByBook(bId);
        reviewAPI.deleteAllReviewByBook(bId);
        verify(reviewService, times(1)).deleteAllByBook(bId);
    }
}