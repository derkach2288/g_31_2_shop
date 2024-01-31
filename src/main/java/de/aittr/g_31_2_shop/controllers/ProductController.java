package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.exception_handling.Response;
import de.aittr.g_31_2_shop.exception_handling.exceptions.FirstTestException;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ProductDto save(@Valid @RequestBody ProductDto product) {
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


    @PutMapping
    public void update(@RequestBody ProductDto product) {
        service.update(product);
    }
//    @PutMapping("/{id}")
//    public void update(@PathVariable int id, @RequestBody ProductDto product) {
//        product.setId(id);
//        service.update(product);
//    }

//    @PutMapping("/delete/id/{id}")
//    public void deleteById(@PathVariable int id) {
//        service.deleteById(id);
//    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }


    @DeleteMapping("/del_by_name/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PutMapping("/{id}")
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

    // 1 способ - создание метода-обработчика в контроллере, где мы ожидаем ошибки
    // Минус - если в разных контроллерах требуется обрабатывать одинаково - придется написать один и тот же обработчик в разных контроллерах
    // Плюс - если в разных контроллерах нужно обрабатывать ошибки по разному - как раз мы можем это сделать
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // BAD_REQUEST
    public Response handleException(FirstTestException e) {
        return new Response(e.getMessage());
    }
}
