package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.CommonProduct;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
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
    public Product save(@RequestBody CommonProduct product){
        return service.save(product);
    }

    @GetMapping
    public List<Product> getAllActiveProducts(){
        return service.getAllActiveProducts();
    }
}
