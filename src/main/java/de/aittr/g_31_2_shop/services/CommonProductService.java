package de.aittr.g_31_2_shop.services;

import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.repositories.interfaces.ProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonProductService implements ProductService {
    private ProductRepository repository;

    public CommonProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {

        return repository.getAll();
    }

    @Override
    public Product getActiveProductById(int id) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(int id) {

    }

    @Override
    public int getActiveProductCount() {
        return 0;
    }

    @Override
    public double getActiveProductTotalPrice() {
        return 0;
    }

    @Override
    public double getActiveProductAveragePrice() {
        return 0;
    }
}
