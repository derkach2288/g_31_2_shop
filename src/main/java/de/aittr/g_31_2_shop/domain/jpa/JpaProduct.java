package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Pear - OK
    //pear - X
    //PEAR - X
    // PEaR - X
    // Pe - X
    // Pear7 -X
    // Pear# - X
    @Column(name = "name")
//    @NotNull
//    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]{3,}")
    private String name;
    @Column(name = "price")
    @Min(10)
    @Max(10000)
    private double price;
    @Column(name = "is_active")
    private boolean isActive;
    @Transient
    private Logger logger = LoggerFactory.getLogger(JpaProduct.class);

    public JpaProduct() {
        logger.info("Вызван пустой конструктор JpaProduct");
    }

    public JpaProduct(int id, String name, double price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = isActive;

        logger.info("Вызван конструктор JpaProduct с id={}, name={}, price={}, isActive={} ", id, name, price, isActive);
    }

    @Override
    public int getId() {
        logger.info("Вызван метод getId");
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
        logger.info("Вызван метод setId c параметром id={}", id);
    }

    @Override
    public String getName() {
        logger.info("Вызван метод getName");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        logger.info("Вызван метод setName c параметром name={}", name);
    }

    @Override
    public double getPrice() {
        logger.info("Вызван метод getPrice");
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        logger.info("Вызван метод setPrice c параметром price={}", price);
    }

    @Override
    public boolean isActive() {
        logger.info("Вызван метод isActive");
        return isActive;
    }

    @Override
    public void setActive(boolean active) {

        this.isActive = active;
        logger.info("Вызван метод setActive c параметром active={}", active);

    }

    @Override
    public boolean equals(Object o) {
        logger.info("Вызван метод equals()");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaProduct that = (JpaProduct) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (isActive != that.isActive) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        logger.info("Вызван метод hashCode()");
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        logger.info("Вызван метод toString()");
        return "JpaProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }
}
