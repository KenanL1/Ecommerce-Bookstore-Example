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
    public ResponseEntity<List<Review>> getReviews(@PathVariable("bid") String bid,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
                                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(reviewService.getReviewsByBid(bid, page, size, sortDir, sortBy).getContent());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
    }

    @DeleteMapping("/book/{bid}")
    public void deleteAllReviewByBook(@PathVariable("bid") String bid) {
        reviewService.deleteAllByBook(bid);
    }

}
