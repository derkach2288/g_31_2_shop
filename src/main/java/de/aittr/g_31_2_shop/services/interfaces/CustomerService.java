package de.aittr.g_31_2_shop.services.interfaces;

import de.aittr.g_31_2_shop.domain.interfaces.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> getAllActiveCustomer();

}
