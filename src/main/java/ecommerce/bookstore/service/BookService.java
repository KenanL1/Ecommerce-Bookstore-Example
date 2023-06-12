package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.enums.Category;
import ecommerce.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Return all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Return list of books where category is input
    public List<Book> getBookByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    //return a book by its Id
    public Optional<Book> getByBid(String bid) {
        return bookRepository.findById(bid);
    }

    //return book where title is like input value
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
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
