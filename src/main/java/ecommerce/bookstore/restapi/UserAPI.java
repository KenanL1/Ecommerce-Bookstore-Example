package ecommerce.bookstore.restapi;

import ecommerce.bookstore.entity.Address;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    // get the user with that username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    // create a new address in the database
    @PostMapping("/address")
    public void addAddress(String street, String province, String country, String zip) {
        userService.addAddress(street, province, country, zip);
    }

    // get the address of that id
    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getAddress(id).get());
    }
}
