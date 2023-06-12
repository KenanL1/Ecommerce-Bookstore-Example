package ecommerce.bookstore.controller;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.service.BookService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    @Enumerated(EnumType.STRING)
    private Category category;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        String cartSize = (String) session.getAttribute("cartSize");
        List<Book> books = bookService.getAllBooks();
        if (cartSize == null)
            cartSize = "0";
        model.addAttribute("categories", Category.values());
        model.addAttribute("cartSize", cartSize);
        return "index";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
