package ecommerce.bookstore.service;

import ecommerce.bookstore.entity.Address;
import ecommerce.bookstore.entity.Cart;
import ecommerce.bookstore.entity.CartItems;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.enums.Role;
import ecommerce.bookstore.repository.AddressRepository;
import ecommerce.bookstore.repository.CartRepository;
import ecommerce.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /* #######################Address Business Logic############# */

    // Create a new address
    public Long addAddress(String street, String province, String country, String zip) {
        Address a = addressRepository.save(new Address(street, province, country, zip, null));
        return a.getId();
    }

    //Get address
    public Optional<Address> getAddress(Long id) {
        return addressRepository.findById(id);
    }


    /* Create a new user with address linked */
    public User addUser(String name, String username, String password, String street, String province, String country, String zip) {
        Long addrId = addAddress(street, province, country, zip);
        User u = new User(name, username, addrId, passwordEncoder.encode(password), Role.USER);
        cartRepository.save(new Cart(u, new ArrayList<CartItems>()));
        return userRepository.save(u);
    }

    // Create a new admin
    public User addAdmin(String username, String name, Long addr, String password) {
        User u = new User(name, username, addr, password, Role.ADMIN);
        return userRepository.save(u);
    }

    //Get a user based off username
    public User getUser(String userName) {
        return userRepository.findByUsername(userName);
    }
}
