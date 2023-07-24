package ecommerce.bookstore.repository;

import ecommerce.bookstore.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review WHERE bid = :bid", nativeQuery = true)
    Page<Review> findReviewsByBid(@Param("bid") String bid, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM review WHERE bid = :bid", nativeQuery = true)
    void deleteAllByBook(@Param("bid") String bid);

}
