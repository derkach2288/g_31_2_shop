package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
    @OneToOne(mappedBy = "customer") // имя поля в классе Cart (28 строка)
    private JpaCart cart;

    public JpaCustomer() {
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
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void setCart(Cart cart) {
        try {
            this.cart = (JpaCart) cart;
        } catch (Exception e) {
            throw new IllegalArgumentException("В сеттер JpaCustomer передан несовместимый тип корзины!");
        }
    }

    @Override
    public boolean equals(Object o) {
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
        int result = id;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JpaCustomer{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", cart=" + cart +
                '}';
    }
}
