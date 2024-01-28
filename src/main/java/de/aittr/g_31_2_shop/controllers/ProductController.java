package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto product) {
        return service.save(product);
    }

    @GetMapping
    public List<ProductDto> getAllActiveProducts() {
        return service.getAllActiveProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getActiveProductById(@PathVariable int id) {
        return service.getActiveProductById(id);
    }


    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody ProductDto product) {
        product.setId(id);
        service.update(product);
    }

    @PutMapping("/delete/id/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @PutMapping("/delete/name/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PutMapping("/restore/{id}")
    public void restoreById(@PathVariable int id) {
        service.restoreById(id);
    }

    @GetMapping("/count")
    public int getActiveProductCount() {
        return service.getActiveProductCount();
    }
    @GetMapping("/total")
    public double getActiveProductTotalPrice(){
        return service.getActiveProductTotalPrice();
    }
    @GetMapping("/avg")
    public double getActiveProductAveragePrice(){
        return service.getActiveProductAveragePrice();
    }


}
