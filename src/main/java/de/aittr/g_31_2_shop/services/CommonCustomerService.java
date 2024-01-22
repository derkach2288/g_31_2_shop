package de.aittr.g_31_2_shop.services;

import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCustomerService implements CustomerService {

    private CustomerRepository repository;

    public CommonCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomer() {
        return repository.getAll();
    }
}
