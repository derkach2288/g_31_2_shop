package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
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
        JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
        entity.setId(0);
        entity = repository.save(entity);
        return mappingService.mapProductEntityToDto(entity);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAllByIsActiveTrue()
                .stream()
                .map(p -> mappingService.mapProductEntityToDto(p))
                .toList();
    }

    @Override
    public ProductDto getActiveProductById(int id) {
        JpaProduct productById = repository.findByIdAndIsActiveTrue(id);

        if (productById != null) {
            return mappingService.mapProductEntityToDto(productById);
        } else {
            return null;
        }

    }

    @Override
    public void update(ProductDto product) {
        JpaProduct updateJpaProduct = mappingService.mapDtoToJpaProduct(product);
        repository.updateProduct(updateJpaProduct);
    }

    @Override
    public void deleteById(int id) {
        repository.setIsActiveFalseById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public void restoreById(int id) {
        repository.restoreById(id);
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
