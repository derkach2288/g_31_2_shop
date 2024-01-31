package de.aittr.g_31_2_shop.repositories.jpa;

import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<JpaProduct, Integer> {

    @Transactional
    List<JpaProduct> findAllByIsActiveTrue();
    @Transactional
    JpaProduct findByIdAndIsActiveTrue(int id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET name = :#{#product.name}, price = :#{#product.price}, is_active = :#{#product.isActive} WHERE id = :#{#product.id}", nativeQuery = true)
    void updateProduct(@Param("product") JpaProduct product);
    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET is_active = 0 WHERE id = :id", nativeQuery = true)
    void setIsActiveFalseById(@Param("id") int id);

    JpaProduct findByName(String name);
    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET is_active = 0 WHERE name = :name", nativeQuery = true)
    void deleteByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET is_active = 1 WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") int id);

    @Transactional
    int countFindAllByIsActiveTrue();

    @Query(value = "SELECT SUM(price) FROM product WHERE is_active = 1;", nativeQuery = true)
    double getActiveProductTotalPrice();
    @Query(value = "SELECT ROUND(AVG(price), 2) FROM product WHERE is_active = 1;", nativeQuery = true)
    double getActiveProductAveragePrice();

}
