package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.repository.BookRepository;
import ecommerce.bookstore.repository.CartRepository;
import ecommerce.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;


    public Cart getCart(Long userId) {
        Cart cart = cartRepository.getUserCart(userId);

        if (cart == null) {
            User user = userRepository.findById(userId).get();
            cart = cartRepository.save(new Cart(user, new ArrayList<Book>()));
        }
        return cart;
    }

    public void addBookToCart(String bookId, Long userId) {
        Book book = bookRepository.findById(bookId).get();
        Cart cart = getCart(userId);

        cart.getCartList().add(book);
        cartRepository.save(cart);
    }

    public void removeBookFromCart(Long userId, String bookId) {
        Cart cart = getCart(userId);

        if (cart != null) {
            Book book = bookRepository.findById(bookId).get();
            List<Book> cartList = cart.getCartList();
            cartList.remove(book);
            cart.setCartList(cartList);
            cartRepository.save(cart);
        }
    }

    public void clearCart(Long userId) {
        Cart cart = getCart(userId);

        if (cart != null) {
            cart.getCartList().clear();
            cartRepository.save(cart);
        }
    }
}
