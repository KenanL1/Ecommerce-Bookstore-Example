package ecommerce.bookstore.repository;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategory(Category category);

    @Query(value = "SELECT * FROM book where UPPER(title) LIKE UPPER('%Heart%')", nativeQuery = true)
    List<Book> findByTitle(@Param("title") String title);

}
