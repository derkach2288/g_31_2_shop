package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.jdbc.CommonCustomer;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerDto save(@Valid @RequestBody CustomerDto customer){

        return service.save(customer);
    }

    @GetMapping
    public List<CustomerDto> getAll() {

        return service.getAllActiveCustomers();
    }
    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable int id) {
        return service.getActiveCustomerById(id);
    }

    @PutMapping("/add_product")
    public void addProductToCart(@RequestParam int customerId, @RequestParam int productId) {
        service.addProductToCart(customerId, productId);
    }
}
