package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaCart;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.exception_handling.exceptions.CustomerNotFoundException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.CustomerValidationException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.InactiveProductException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaCartRepository;
import de.aittr.g_31_2_shop.repositories.jpa.JpaCustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.CustomerMappingService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaCustomerService implements CustomerService {

    private JpaCustomerRepository repository;
    private CustomerMappingService mappingService;
    private JpaCartRepository cartRepository;

    private JpaProductService jpaProductService;

    public JpaCustomerService(JpaCustomerRepository repository, CustomerMappingService mappingService, JpaCartRepository cartRepository, JpaProductService jpaProductService) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.cartRepository = cartRepository;
        this.jpaProductService = jpaProductService;
    }

    @Override
    @Transactional
    public CustomerDto save(CustomerDto dto) {
        JpaCustomer entity = mappingService.mapDtoToJpaCustomer(dto);

        JpaCart cart = new JpaCart();
        entity.setCart(cart);
        cart.setCustomer(entity);

        try {
            entity = repository.save(entity);

            return mappingService.mapCustomerEntityToDto(entity);

        } catch (Exception e) {
            throw new CustomerValidationException("Ошибка валидации пользователя: " + e.getMessage());
        }

    }


//    @Override
//    public CustomerDto save(CustomerDto dto) {
//
//        try {
//            JpaCustomer jpaCustomer = mappingService.mapDtoToJpaCustomer(dto);
//
//            // Устанавливаем id пользователя в поле customer_id корзины
//            jpaCustomer.setId(0);
//
//            // Сохраняем пользователя (должен присвоиться новый id)
//            jpaCustomer = repository.save(jpaCustomer);
//
//            // Создаем новую корзину и устанавливаем ее для пользователя
//            JpaCart jpaCart = new JpaCart();
//            jpaCustomer.setCart(jpaCart);
//
//            // Устанавливаем пользователя для корзины
//            jpaCart.setCustomer(jpaCustomer);
//
//            // Сохраняем корзину (теперь у нее есть ссылка на нового пользователя)
//            jpaCart = cartRepository.save(jpaCart);
//
//
//
//            return mappingService.mapCustomerEntityToDto(jpaCustomer);
//
//        } catch (Exception e) {
//            throw new CustomerValidationException("Ошибка валидации пользователя: " + e.getMessage());
//        }
//    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {

        return repository.findAll()
                .stream()
                .filter(c -> c.isActive())
                .map(c -> mappingService.mapCustomerEntityToDto(c))
                .toList();
    }

    @Override
    public CustomerDto getActiveCustomerById(int id) {
        JpaCustomer entity = repository.findById(id).orElse(null);

        if (entity != null && entity.isActive()) {
            return mappingService.mapCustomerEntityToDto(entity);
        }
        return null;
    }

    @Override
    public void update(CustomerDto customer) {

    }

    @Override
    public void deleteBuId(int id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(int id) {

    }

    @Override
    public int getActiveCustomersCount() {
        return 0;
    }

    @Override
    public double getCartTotalPriceById(int customerId) {
        return 0;
    }

    @Override
    public double getAverageProductPriceById(int customerId) {
        return 0;
    }

    @Override
    @Transactional
    public void addProductToCart(int customerId, int productId) {

        Product product = jpaProductService.getActiveJpaProductById(productId);
        JpaCustomer customer = repository.findById(customerId).orElse(null);
        if (product == null) {
            throw new CustomerNotFoundException(String.format(
                    "There is no customer with id [%d] in the database", customerId));
        }

        if (!customer.isActive()) {
            throw new InactiveProductException(String.format(
                    "Customer with id [%d] is inactive and cannot be retrieved", customerId));
        }

            customer.getCart().addProduct(product);

    }

    @Override
    public void deleteProductFromCart(int customerId, int productId) {

    }

    @Override
    public void clearCartById(int customerId) {

    }
}
