package de.aittr.g_31_2_shop.services.interfaces;

import de.aittr.g_31_2_shop.domain.interfaces.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> getAllActiveCustomers();
    Customer getActiveCustomerById(int id);
    void update(Customer customer);
    void deleteBuId(int id);
    void deleteByName(String name);
    void restoreById(int id);
    int getActiveCustomersCount();
    double getCartTotalPriceById(int customerId);
    double getAverageProductPriceById(int customerId);
    void addProductToCart(int customerId, int productId);
    void deleteProductFromCart(int customerId, int productId);
    void clearCartById(int customerId);

}
