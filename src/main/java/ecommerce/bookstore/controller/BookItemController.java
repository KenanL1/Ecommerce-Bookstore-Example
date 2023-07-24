package ecommerce.bookstore.controller;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.service.BookService;
import ecommerce.bookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookItemController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @GetMapping("/{bid}")
    public String BookItem(Model model, @PathVariable("bid") String bid,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
                           @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        Page<Review> reviews = reviewService.getReviewsByBid(bid, page, size, sortDir, sortBy);

        int totalPages = reviews.getTotalPages();
        long totalReviews = reviews.getTotalElements();
        Optional<Book> book = bookService.getByBid(bid);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("book", book.get());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalReviews", totalReviews);
        return "bookItem";
    }
}
