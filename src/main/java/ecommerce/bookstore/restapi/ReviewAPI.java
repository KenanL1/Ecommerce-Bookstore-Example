package ecommerce.bookstore.restapi;

import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewAPI {

    @Autowired
    private ReviewService reviewService;

    // add a book review
    @PostMapping("")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.insert(review.getBid(), review.getReview(), review.getRating()));
    }

    @GetMapping("book/{bid}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable("bid") String bid) {
        return ResponseEntity.ok(reviewService.getReviewsForBid(bid));
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
    }

    @DeleteMapping("/book/{bId}")
    public void deleteAllReviewByBook(@PathVariable("bId") String bId) {
        reviewService.deleteAllByBook(bId);
    }

}
