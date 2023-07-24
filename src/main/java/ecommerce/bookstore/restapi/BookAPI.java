package ecommerce.bookstore.restapi;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/book")
public class BookAPI {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping("")
    public ResponseEntity<?> getBooks(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size,
                                      @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
                                      @RequestParam(value = "sortBy", defaultValue = "bid") String sortBy) {


        // Display books with the searched category
        if (category != null && !category.equalsIgnoreCase("ALL")) {
            try {
                Category categoryEnum = Category.valueOf(category.toUpperCase());
                if (title != null) {
                    return ResponseEntity.ok(
                            bookService.getBooksByCategoryAndTitle(categoryEnum, title, page, size, sortDir, sortBy)
                    );
                }
                return ResponseEntity.ok(
                        bookService.getBookByCategory(categoryEnum, page, size, sortDir, sortBy)
                );
            } catch (IllegalArgumentException e) {
                // Handle the case when an invalid category is provided
                return ResponseEntity.badRequest().body("Invalid category");
            }
        }

        // Display books with the searched title
        if (title != null)
            return ResponseEntity.ok(
                    bookService.getBooksByTitle(title, page, size, sortDir, sortBy)
            );

        // Display all books
        return ResponseEntity.ok(
                bookService.getAllBooks(page, size, sortDir, sortBy)
        );

    }

    // Get the book with the specific bid
    @GetMapping("/{bid}")
    public ResponseEntity<Book> retrieveBook(@PathVariable("bid") String bid) throws Exception {
        Optional<Book> book = bookService.getByBid(bid);
        if (book.isPresent())
            return ResponseEntity.ok(book.get());
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // add a new book
    @PostMapping
    public ResponseEntity<?> bookAdd(String bid, String title, double price, Category category, String author, String picture_link) {
        bookService.insert(bid, title, price, category, author, picture_link);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book added from database");
        return ResponseEntity.ok(response);
    }

    // delete a book
    @DeleteMapping("/{bid}")
    public ResponseEntity<Map<String, Object>> bookDelete(@PathVariable("bid") String bid) {
        bookService.deleteBook(bid);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book removed from database");
        return ResponseEntity.ok(response);
    }

}
