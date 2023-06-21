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
                                      @RequestParam(required = false) String title) {


        if (category != null && !category.equalsIgnoreCase("ALL")) {
            try {
                Category categoryEnum = Category.valueOf(category.toUpperCase());
                return ResponseEntity.ok(bookService.getBookByCategory(categoryEnum));
            } catch (IllegalArgumentException e) {
                // Handle the case when an invalid category is provided
                return ResponseEntity.badRequest().body("Invalid category");
            }
        }

        if (title != null)
            return ResponseEntity.ok(bookService.getBooksByTitle(title));

        return ResponseEntity.ok(bookService.getAllBooks());

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
