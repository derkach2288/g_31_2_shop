package de.aittr.g_31_2_shop.domain.jdbc;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;

import java.util.Objects;

public class CommonCustomer implements Customer {

    private int id;
    private boolean isActive;
    private String name;
    private Cart cart;

    public CommonCustomer() {
        this.isActive = true;
    }

    public CommonCustomer(int id, boolean isActive, String name, Cart cart) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.cart = cart;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public String getName() {
        return name;
    }


    @Override
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonCustomer that = (CommonCustomer) o;

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
        return String.format("Покупатель: ИД - %d, имя - %s, активен - %s, содержимое корзины:%n%s",
                id, name, isActive ? "да" : "нет", cart);
    }
}
