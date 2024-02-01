package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.jpa.JpaCart;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import de.aittr.g_31_2_shop.exception_handling.exceptions.CustomerValidationException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaCartRepository;
import de.aittr.g_31_2_shop.repositories.jpa.JpaCustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import de.aittr.g_31_2_shop.services.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JpaCustomerService implements CustomerService {

    private JpaCustomerRepository repository;
    private CustomerMappingService mappingService;
    private JpaCartRepository cartRepository;

    public JpaCustomerService(JpaCustomerRepository repository, CustomerMappingService mappingService, JpaCartRepository cartRepository) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CustomerDto save(CustomerDto dto) {

        try {
            JpaCustomer jpaCustomer = mappingService.mapDtoToJpaCustomer(dto);

            // Устанавливаем id пользователя в поле customer_id корзины
            jpaCustomer.setId(0);

            // Сохраняем пользователя (должен присвоиться новый id)
            jpaCustomer = repository.save(jpaCustomer);

            // Создаем новую корзину и устанавливаем ее для пользователя
            JpaCart jpaCart = new JpaCart();
            jpaCustomer.setCart(jpaCart);

            // Устанавливаем пользователя для корзины
            jpaCart.setCustomer(jpaCustomer);

            // Сохраняем корзину (теперь у нее есть ссылка на нового пользователя)
            jpaCart = cartRepository.save(jpaCart);



            return mappingService.mapCustomerEntityToDto(jpaCustomer);

        } catch (Exception e) {
            throw new CustomerValidationException("Ошибка валидации пользователя: " + e.getMessage());
        }
    }

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
    public void addProductToCart(int customerId, int productId) {

    }

    @Override
    public void deleteProductFromCart(int customerId, int productId) {

    }

    @Override
    public void clearCartById(int customerId) {

    }
}
