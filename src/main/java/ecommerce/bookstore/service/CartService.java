package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Book;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.CartItems;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.repository.BookRepository;
import ecommerce.bookstore.repository.CartItemRepository;
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
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;


    public Cart getCart(Long userId) {
        Cart cart = cartRepository.getUserCart(userId);

        if (cart == null) {
            User user = userRepository.findById(userId).get();
            cart = cartRepository.save(new Cart(user, new ArrayList<CartItems>()));
        }
        return cart;
    }

    public void addBookToCart(String bookId, Long userId) {
        Book book = bookRepository.findById(bookId).get();
        CartItems item = new CartItems(book, 1);
        cartItemRepository.save(item);
        Cart cart = getCart(userId);

        cart.getCartList().add(item);
        cartRepository.save(cart);
    }

    public boolean checkBookInCart(String bookId, Long userId) {
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
    }

    public void updateBookQuantity(String bookId, Long userId, int quantity) {
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
    }

    public void removeBookFromCart(Long userId, String bookId) {
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
    }

    public void clearCart(Long userId) {
        Cart cart = getCart(userId);

        if (cart != null) {
            List<CartItems> cartList = cart.getCartList();
            cart.getCartList().clear();
            for (CartItems item : cartList) {
                cartItemRepository.delete(item);
            }
            cartRepository.save(cart);
        }
    }
}
