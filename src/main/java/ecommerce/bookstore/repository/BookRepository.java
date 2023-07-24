package ecommerce.bookstore.repository;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// DAO
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findByCategory(Category category, Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE UPPER(title) LIKE CONCAT('%', UPPER(:title), '%')", nativeQuery = true)
    Page<Book> findByTitle(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE UPPER(title) LIKE CONCAT('%', UPPER(:title), '%') AND category = :categoryName", nativeQuery = true)
    Page<Book> findByCategoryAndTitle(@Param("categoryName") String categoryName, @Param("title") String title, Pageable pageable);

}
