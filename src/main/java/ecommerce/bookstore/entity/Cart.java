package ecommerce.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> cartList = new ArrayList<Book>();

    public Cart(User user, List<Book> cartList) {
        this.user = user;
        this.cartList = cartList;
    }

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0.0;
        for (Book b : getCartList()) {
            sum += b.getPrice();
        }
        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        return getCartList().size();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getCartList() {
        return cartList;
    }

    public void setCartList(List<Book> orderList) {
        this.cartList = orderList;
    }
}
