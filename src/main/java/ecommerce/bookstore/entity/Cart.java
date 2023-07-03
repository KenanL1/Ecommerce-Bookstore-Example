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
    private List<CartItems> cartList = new ArrayList<CartItems>();

    public Cart(User user, List<CartItems> cartList) {
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
        for (CartItems b : getCartList()) {
            sum += b.getTotalPrice();
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

    public List<CartItems> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartItems> orderList) {
        this.cartList = orderList;
    }
}
