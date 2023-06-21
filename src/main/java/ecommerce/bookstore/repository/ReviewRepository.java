package ecommerce.bookstore.repository;

import ecommerce.bookstore.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review WHERE bid = :bid", nativeQuery = true)
    List<Review> findReviewsForBid(@Param("bid") String bid);

    @Modifying
    @Query(value = "DELETE FROM review WHERE bid = :bid", nativeQuery = true)
    void deleteAllByBook(@Param("bid") String bid);

}
