package de.aittr.g_31_2_shop.domain.dto;

import de.aittr.g_31_2_shop.domain.jpa.JpaCart;

import java.util.Objects;

public class CustomerDto {
    private int id;
    private String name;
    private int age;
    private String email;

    private CartDto cart;

//    public CustomerDto(int id, String name, CartDto cart) {
//        this.id = id;
//        this.name = name;
//        this.cart = cart;
//    }

    public CustomerDto(int id, String name, int age, String email, CartDto cart) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public CartDto getCart() {
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDto that = (CustomerDto) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cart=" + cart +
                '}';
    }
}
