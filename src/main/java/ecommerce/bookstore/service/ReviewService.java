package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    private ReviewRepository reviewRepository;

    // Returns list reviews for a bid
    public Page<Review> getReviewsByBid(String bid) {
        return getReviewsByBid(bid, 0, 10, "desc", "id");
    }

    // Returns list reviews for a bid given page, size, sort direction and sort by
    public Page<Review> getReviewsByBid(String bid, int page, int size, String sortDir, String sortBy) {
        try {
            // Handle pagination
            PageRequest pageable = PageRequest.of(page, size);

            // Handle sorting
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            pageable = pageable.withSort(sort);

            return reviewRepository.findReviewsByBid(bid, pageable);
        } catch (Exception e) {
            logger.error("Error getting reviews", e);
            throw e;
        }
    }

    // Insert review into db
    public Review insert(String bid, String review, int rating) {
        try {
            if (bid == null || review == null) {
                throw new IllegalArgumentException();
            }
            Review r = new Review(bid, review, rating);
            return reviewRepository.save(r);
        } catch (Exception e) {
            logger.error("Error creating review", e);
            throw e;
        }
    }

    // Delete a review by review id
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    // Delete all review of a book id
    public void deleteAllByBook(String bid) {
        reviewRepository.deleteAllByBook(bid);
    }

    // Gets most reviewed books
//    public List<Book> getMostReviewed() {
//        String query = "SELECT COUNT(BookStore2021.Review.bid) as NumberOfReviews, BookStore2021.Review.bid,  BookStore2021.Book.title\n" +
//                "FROM BookStore2021.Review\n" +
//                "INNER JOIN  BookStore2021.Book\n" +
//                "ON BookStore2021.Book.bid = BookStore2021.Review.bid\n" +
//                "GROUP BY BookStore2021.Review.bid\n" +
//                "ORDER BY COUNT(BookStore2021.Review.bid) DESC\n" +
//                "LIMIT 10;";
//        PreparedStatement p = this.con.prepareStatement(query);
//        ResultSet r = p.executeQuery(query);
//        List<AnalyticsBean> rv = new ArrayList<AnalyticsBean>();
//        while (r.next()){
//            String bid = r.getString("bid");
//            int count = r.getInt("NumberOfReviews");
//            String title = r.getString("title");
//            rv.add( new AnalyticsBean("Review", count, bid, title));
//
//
//        }
//
//        return rv;
//
//    }
}
