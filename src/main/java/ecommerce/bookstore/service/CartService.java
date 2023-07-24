package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.CartItems;
import ecommerce.bookstore.repository.BookRepository;
import ecommerce.bookstore.repository.CartItemRepository;
import ecommerce.bookstore.repository.CartRepository;
import ecommerce.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;

    // Get user cart
    public Cart getCart(Long userId) {
        try {
            return cartRepository.getUserCart(userId);
        } catch (Exception e) {
            logger.error("Error getting cart", e);
            throw e;
        }
    }

    // Add book to users cart
    public void addBookToCart(String bookId, Long userId) {
        try {
            Book book = bookRepository.findById(bookId).get();
            CartItems item = new CartItems(book, 1);
            cartItemRepository.save(item);
            Cart cart = getCart(userId);

            cart.getCartList().add(item);
            cartRepository.save(cart);
        } catch (Exception e) {
            logger.error("Error adding book to cart", e);
            throw e;
        }
    }

    // Check if a book is already in the users cart
    public boolean checkBookInCart(String bookId, Long userId) {
        try {
            Cart cart = getCart(userId);

            if (cart != null) {
                List<CartItems> cartList = cart.getCartList();
                for (CartItems item : cartList) {
                    if (item.getBook().getBid().equals(bookId)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking if book in cart", e);
            throw e;
        }
    }

    // Update the book quantity in users cart
    public void updateBookQuantity(String bookId, Long userId, int quantity) {
        try {
            Cart cart = getCart(userId);

            if (cart != null) {
                List<CartItems> cartList = cart.getCartList();
                for (CartItems item : cartList) {
                    if (item.getBook().getBid().equals(bookId)) {
                        item.setQuantity(quantity);
                        cart.setCartList(cartList);
                        cartItemRepository.save(item);
                        cartRepository.save(cart);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error updating quantity", e);
            throw e;
        }
    }

    // Remove a book from the users cart
    public void removeBookFromCart(Long userId, String bookId) {
        try {
            Cart cart = getCart(userId);

            if (cart != null) {
                //            Book book = cartItemRepository.findById(bookId).get();
                List<CartItems> cartList = cart.getCartList();
                //            cartList.remove(book);
                for (int i = 0; i < cartList.size(); i++) {
                    if (cartList.get(i).getBook().getBid().equals(bookId)) {
                        CartItems item = cartList.get(i);
                        cartList.remove(i);
                        cartItemRepository.delete(item);
                        break;
                    }
                }
                cart.setCartList(cartList);
                cartRepository.save(cart);
            }
        } catch (Exception e) {
            logger.error("Error removing book from cart", e);
            throw e;
        }
    }

    // Clear all cart items
    public void clearCart(Long userId) {
        try {
            Cart cart = getCart(userId);

            if (cart != null) {
                List<CartItems> cartList = cart.getCartList();
                cart.getCartList().clear();
                for (CartItems item : cartList) {
                    cartItemRepository.delete(item);
                }
                cartRepository.save(cart);
            }
        } catch (Exception e) {
            logger.error("Error clearing cart", e);
            throw e;
        }
    }
}
