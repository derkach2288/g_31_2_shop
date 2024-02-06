package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<JpaProduct> products = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "customer_id")
    private JpaCustomer customer;

//    @Transient
//    private Logger logger = LoggerFactory.getLogger(JpaCart.class);


    public JpaCart() {
//        logger.info("Вызван пустой конструктор JpaCart");
    }

    public JpaCart(int id, List<JpaProduct> products) {
        this.id = id;
        this.products = products;
//        logger.info("Вызван конструктор JpaCart с id={}, products={}", id, products);
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
    public List<Product> getProducts() {
        //TODO посмотреть как будет на практике, потом переделать
//        logger.info("Вызван метод getProducts");
        return new ArrayList<>(products);
    }

    @Override
    public void addProduct(Product product) {
        try {
            products.add((JpaProduct) product);
//            logger.info("Вызван метод addProduct c параметром product={}", product);
        } catch (Exception e) {
            throw new IllegalArgumentException("В с корзину JpaCart помещен несовместимый тип продукта!");
        }


    }

    @Override
    public void deleteProductById(int productId) {
        // TODO проверить работу на практике и при необходимости переделать
        products.removeIf(p -> p.getId() == productId);
//        logger.info("Вызван метод deleteProductById c параметром productId={}", productId);
    }

    @Override
    public void clear() {
        products.clear();
//        logger.info("Вызван метод clear");
    }

    @Override
    public double getTotalPrice() {
//        logger.info("Вызван метод getTotalPrice");
        return products.stream()
                .filter(p -> p.isActive())
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    @Override
    public double getAveragePrice() {
//        logger.info("Вызван метод getAveragePrice");
        return products.stream()
                .filter(p -> p.isActive())
                .mapToDouble(p -> p.getPrice())
                .average()
                .orElse(0);
    }

    public void setProducts(List<JpaProduct> products) {
        this.products = products;
//        logger.info("Вызван метод setProducts c параметром products={}", products);
    }

    public Customer getCustomer() {
//        logger.info("Вызван метод getCustomer");
        return customer;
    }

    public void setCustomer(JpaCustomer customer) {
        this.customer = customer;
//        logger.info("Вызван метод setCustomer c параметром customer={}", customer);
    }

    @Override
    public boolean equals(Object o) {
//        logger.info("Вызван метод equals()");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaCart jpaCart = (JpaCart) o;

        if (id != jpaCart.id) return false;
        if (!Objects.equals(products, jpaCart.products)) return false;
        return Objects.equals(customer, jpaCart.customer);
    }

    @Override
    public int hashCode() {
//        logger.info("Вызван метод hashCode()");
        int result = id;
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
//        logger.info("Вызван метод toString()");
        return "JpaCart{" +
                "id=" + id +
                ", products=" + products +
                ", customer=" + customer +
                '}';
    }
}
