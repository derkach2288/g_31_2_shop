package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "is_Active")
    private boolean isActive;
    @Column(name = "name")
    @Pattern(regexp = "[A-Z][a-z]{2,}")
    private String name;
    @Column(name = "age")
    @Min(0)
    private int age;
    @Column(name = "email")
    @Email
    private String email;
//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true) // имя поля в классе Cart (28 строка)
    @OneToOne(mappedBy = "customer") // имя поля в классе Cart (28 строка)
    private JpaCart cart;
//    @Transient
//    private Logger logger = LoggerFactory.getLogger(JpaCustomer.class);


    public JpaCustomer() {
//        logger.info("Вызван пустой конструктор JpaCustomer");
    }

//    public JpaCustomer(int id, boolean isActive, String name, JpaCart cart) {
//        this.id = id;
//        this.isActive = isActive;
//        this.name = name;
//        this.cart = cart;
//    }

    public JpaCustomer(int id, boolean isActive, String name, int age, String email, JpaCart cart) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.age = age;
        this.email = email;
        this.cart = cart;

//        logger.info("Вызван конструктор JpaCustomer с id={}, isActive={}, name={}, age={}, email={} cart={}", id, isActive, name, age, email, cart);
    }

    @Override
    public int getId() {
//        logger.info("Вызван метод getId");
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
//        logger.info("Вызван метод setId c параметром id={}", id);
    }

    @Override
    public boolean isActive() {
//        logger.info("Вызван метод isActive");
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
//        logger.info("Вызван метод setActive c параметром active={}", active);
    }

    @Override
    public int getAge() {
//        logger.info("Вызван метод getAge");
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
//        logger.info("Вызван метод setAge c параметром age={}", age);
    }

    @Override
    public String getEmail() {
//        logger.info("Вызван метод getEmail");
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
//        logger.info("Вызван метод setEmail c параметром email={}", email);
    }

    @Override
    public String getName() {
//        logger.info("Вызван метод getName");
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
//        logger.info("Вызван метод setName c параметром name={}", name);
    }

    @Override
    public Cart getCart() {
//        logger.info("Вызван метод getCart");
        return cart;
    }

    @Override
    public void setCart(Cart cart) {
        try {
            this.cart = (JpaCart) cart;
//            logger.info("Вызван метод setCart c параметром cart={}", cart);
        } catch (Exception e) {
            throw new IllegalArgumentException("В сеттер JpaCustomer передан несовместимый тип корзины!");
        }
    }

    @Override
    public boolean equals(Object o) {
//        logger.info("Вызван метод equals()");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaCustomer that = (JpaCustomer) o;

        if (id != that.id) return false;
        if (isActive != that.isActive) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
//        logger.info("Вызван метод hashCode()");
        int result = id;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
//        logger.info("Вызван метод toString()");
        return "JpaCustomer{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", cart=" + cart +
                '}';
    }
}
