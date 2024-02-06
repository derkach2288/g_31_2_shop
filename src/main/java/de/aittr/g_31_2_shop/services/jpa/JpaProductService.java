package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.exception_handling.exceptions.*;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.scheduling.ScheduleExecutor;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaProductService implements ProductService {

    private JpaProductRepository repository;
    private ProductMappingService mappingService;
    //org.apache.logging.log4j.Level;
//    private Logger logger = LogManager.getLogger(JpaProductService.class);

    //org.slf4j.Logger;
    private Logger logger = LoggerFactory.getLogger(JpaProductService.class);

    public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }


    @Override
    public ProductDto save(ProductDto dto) {
        try {
            JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
            entity.setId(0);
            entity = repository.save(entity);
            return mappingService.mapProductEntityToDto(entity);
        } catch (Exception e) {
            throw new ProductValidationException("Incorrect values of product fields", e);
        }
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        Task task = new Task("Method getAllActiveProducts called");
        ScheduleExecutor.scheduleAndExecuteTask(task);
        // здесь будет JoinPoint, сюда будет внедрятся вспомолательный код
        return repository.findAll()
                .stream()
                .filter(p -> p.isActive())
                .map(p -> mappingService.mapProductEntityToDto(p))
                .toList();
//        return repository.findAllByIsActiveTrue()
//                .stream()
//                .map(p -> mappingService.mapProductEntityToDto(p))
//                .toList();
    }

    @Override
    public ProductDto getActiveProductById(int id) {

//        logger.log(Level.INFO, String.format("Запрошен продукт с идендификатором %d.", id));
//        logger.log(Level.WARN, String.format("Запрошен продукт с идендификатором %d.", id));
//        logger.log(Level.ERROR, String.format("Запрошен продукт с идендификатором %d.", id));

//        logger.info(String.format("Запрошен продукт с идендификатором %d.", id));
//        logger.warn(String.format("Запрошен продукт с идендификатором %d.", id));
//        logger.error(String.format("Запрошен продукт с идендификатором %d.", id));


        Product product = repository.findById(id).orElse(null);

        if (product != null && product.isActive()) {
            return mappingService.mapProductEntityToDto(product);
        } else {
            throw new ProductNotFoundException("Продукт с указанным идентификатором отстутсвует в базе данных");
        }


//        JpaProduct productById = repository.findByIdAndIsActiveTrue(id);

//        if (productById != null) {
//            return mappingService.mapProductEntityToDto(productById);
//        } else {
//            return null;
//        }

    }

    @Override
    public void update(ProductDto dto) {

        try {
            JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
            repository.save(entity);
        } catch (Exception e) {
            throw new ProductUpdateException("Ошибка при обновлении продукта: " + e.getMessage());
        }

//        JpaProduct updateJpaProduct = mappingService.mapDtoToJpaProduct(dto);
//        repository.updateProduct(updateJpaProduct);
    }

    @Override
    @Transactional // для того, чтоб продукт оставался в менеджт управляемом состоянии
    public void deleteById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && product.isActive()) {
            product.setActive(false);
        } else {
            throw new ProductNotFoundException("Продукт с указанным идентификатором отстутсвует в базе данных или уже неактивен");
        }


//        repository.setIsActiveFalseById(id);
    }


    @Override
    @Transactional
    public void deleteByName(String name) {
        Product product = repository.findByName(name);

        if (product != null && product.isActive()) {
            product.setActive(false);
        } else {
            throw new ProductNotFoundException("Продукт с указанным именем отстутсвует в базе данных или уже неактивен");
        }


//        repository.deleteByName(name);
    }

    @Override
    @Transactional
    public void restoreById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && !product.isActive()) {
            product.setActive(true);
        } else {
            throw new ProductNotFoundException("Продукт с указанным идентификатором отстутсвует в базе данных или уже активен");
        }


//        repository.restoreById(id);
    }

    @Override
    public int getActiveProductCount() {
        try {
            System.out.println();
            return (int) repository.findAll()
                    .stream()
                    .filter(p -> p.isActive())
                    .count();
//            return getAllActiveProducts().size();
//            return repository.countFindAllByIsActiveTrue();

        } catch (Exception e) {
            throw new ProductCountException("Ошибка при подсчете количества активных продуктов");
        }
    }

    @Override
    public double getActiveProductTotalPrice() {
        try {
            return repository.findAll()
                    .stream()
                    .filter(p -> p.isActive())
                    .mapToDouble(p -> p.getPrice())
                    .sum();
            //        return repository.findAllByIsActiveTrue().stream().mapToDouble(p -> p.getPrice()).sum();
//            return repository.getActiveProductTotalPrice();
        } catch (Exception e) {
            throw new ProductPriceException("Ошибка при вычислении суммарной цены для активных продуктов");
        }
    }

    @Override
    public double getActiveProductAveragePrice() {
        try {
            return repository.findAll()
                    .stream()
                    .filter(p -> p.isActive())
                    .mapToDouble(p -> p.getPrice())
                    .average()
                    .orElse(0);
            //        return repository.findAllByIsActiveTrue().stream().mapToDouble(p -> p.getPrice()).orElse(0);
//            return repository.getActiveProductAveragePrice();
        } catch (Exception e) {
            throw new ProductPriceException("Ошибка при вычислении средней цены для активных продуктов");
        }

    }
}
