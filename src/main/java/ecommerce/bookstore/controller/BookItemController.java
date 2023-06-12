package ecommerce.bookstore.controller;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Review;
import ecommerce.bookstore.service.BookService;
import ecommerce.bookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookItemController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @GetMapping("/{bid}")
    public String BookItem(Model model, @PathVariable("bid") String bid) {
        List<Review> reviews = reviewService.getReviewsForBid(bid);
        Optional<Book> book = bookService.getByBid(bid);
        model.addAttribute("reviews", reviews);
        model.addAttribute("book", book.get());
        return "bookItem";
    }
}
