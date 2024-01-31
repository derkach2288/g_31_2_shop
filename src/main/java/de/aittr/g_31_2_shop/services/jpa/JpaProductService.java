package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.exception_handling.exceptions.FirstTestException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.FourthTestException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.SecondTestException;
import de.aittr.g_31_2_shop.exception_handling.exceptions.ThirdTestException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaProductService implements ProductService {

    private JpaProductRepository repository;
    private ProductMappingService mappingService;

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
            throw new FourthTestException(e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
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
        Product product = repository.findById(id).orElse(null);

        if (product != null && product.isActive()){
            return mappingService.mapProductEntityToDto(product);
        }
        throw new ThirdTestException("Продукт с указанным идентификатором в отстутсвует в базе данных");


//        JpaProduct productById = repository.findByIdAndIsActiveTrue(id);

//        if (productById != null) {
//            return mappingService.mapProductEntityToDto(productById);
//        } else {
//            return null;
//        }

    }

    @Override
    public void update(ProductDto dto) {
        JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
        repository.save(entity);

//        JpaProduct updateJpaProduct = mappingService.mapDtoToJpaProduct(dto);
//        repository.updateProduct(updateJpaProduct);
    }

    @Override
    @Transactional // для того, чтоб продукт оставался в менеджт управляемом состоянии
    public void deleteById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product !=null && product.isActive()){
            product.setActive(false);
        }

//        repository.setIsActiveFalseById(id);
    }


    @Override
    @Transactional
    public void deleteByName(String name) {
        Product product = repository.findByName(name);

        if (product !=null && product.isActive()){
            product.setActive(false);
        }


//        repository.deleteByName(name);
    }

    @Override
    @Transactional
    public void restoreById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product !=null && !product.isActive()){
            product.setActive(true);
        }

//        repository.restoreById(id);
    }

    @Override
    public int getActiveProductCount() {
//        return getAllActiveProducts().size();
        return repository.countFindAllByIsActiveTrue();
    }

    @Override
    public double getActiveProductTotalPrice() {
//        return repository.findAllByIsActiveTrue().stream().mapToDouble(p -> p.getPrice()).sum();
        return repository.getActiveProductTotalPrice();
    }

    @Override
    public double getActiveProductAveragePrice() {
        return repository.getActiveProductAveragePrice();
    }
}
