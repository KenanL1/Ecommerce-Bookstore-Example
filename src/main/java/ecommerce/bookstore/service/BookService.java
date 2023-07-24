package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAllBooks() {
        return getAllBooks(0, 50, "desc", "bid");
    }

    // Return all books
    public Page<Book> getAllBooks(int page, int size, String sortDir, String sortBy) {
        try {
            // Handle pagination
            PageRequest pageable = PageRequest.of(page, size);

            // Handle sorting
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            pageable = pageable.withSort(sort);

            return bookRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error getting Books", e);
            throw e;
        }

    }

    public Page<Book> getBookByCategory(Category category) {
        return getBookByCategory(category, 0, 50, "desc", "bid");
    }

    // Return list of books where category is input
    public Page<Book> getBookByCategory(Category category, int page, int size,
                                        String sortDir, String sortBy) {
        try {
            // Handle pagination
            PageRequest pageable = PageRequest.of(page, size);

            // Handle sorting
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            pageable = pageable.withSort(sort);

            return bookRepository.findByCategory(category, pageable);
        } catch (Exception e) {
            logger.error("Error getting Books by category", e);
            throw e;
        }
    }

    public Page<Book> getBooksByTitle(String title) {
        return getBooksByTitle(title, 0, 50, "desc", "bid");
    }

    //return book where title is like input value
    public Page<Book> getBooksByTitle(String title, int page, int size,
                                      String sortDir, String sortBy) {
        try {
            // Handle pagination
            PageRequest pageable = PageRequest.of(page, size);

            // Handle sorting
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            pageable = pageable.withSort(sort);

            return bookRepository.findByTitle(title, pageable);
        } catch (Exception e) {
            logger.error("Error getting books by title", e);
            throw e;
        }
    }

    public Page<Book> getBooksByCategoryAndTitle(Category category, String title) {
        return getBooksByCategoryAndTitle(category, title, 0, 50, "desc", "bid");
    }

    public Page<Book> getBooksByCategoryAndTitle(Category category, String title, int page, int size,
                                                 String sortDir, String sortBy) {
        try {
            // Handle pagination
            PageRequest pageable = PageRequest.of(page, size);

            // Handle sorting
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            pageable = pageable.withSort(sort);

            return bookRepository.findByCategoryAndTitle(category.name(), title, pageable);
        } catch (Exception e) {
            logger.error("Error getting books by category and title", e);
            throw e;
        }
    }

    //return a book by its Id
    public Optional<Book> getByBid(String bid) {
        return bookRepository.findById(bid);
    }


    //Insert book into db
    public Book insert(String bid, String title, double price, Category category, String author, String picture_link) {
        Book book = new Book(bid, title, price, category, author, picture_link);
        return bookRepository.save(book);
    }

    //Delete a book
    public void deleteBook(String bid) {
        bookRepository.deleteById(bid);
    }


}
