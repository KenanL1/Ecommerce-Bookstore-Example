package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Returns list reviews for a bid
    public List<Review> getReviewsForBid(String bid) {
        return reviewRepository.findReviewsForBid(bid);
    }

    // Insert review into db
    public Review insert(String bid, String review, int rating) {
        Review r = new Review(bid, review, rating);
        return reviewRepository.save(r);
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
