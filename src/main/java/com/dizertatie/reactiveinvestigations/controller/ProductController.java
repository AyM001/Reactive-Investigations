package com.dizertatie.reactiveinvestigations.controller;

import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import com.dizertatie.reactiveinvestigations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/interceptTraffic")
    public List<ProductModel> interceptTraffic() throws InterruptedException {
        productService.generateRandomTraffic();
        return productService.getProductList();
    }

    @GetMapping(value = "/interceptTrafficStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductModel> interceptTrafficReactive() throws InterruptedException {
        productService.generateRandomTraffic();
        return productService.getProductsReactive();
    }

    @GetMapping("/productsByTarget/{id}")
    public List<ProductModel> retrieveProductsOfTarget(@PathVariable Long id){
        return productService.findProductOfTarget(id);
    }

    @GetMapping("/productsByParticipant/{id}")
    public List<ProductModel> retrieveProductsOfParticipant(@PathVariable Long id){
        return productService.findProductOfTarget(id);
    }

    // TODO create methods to retrieve by every field.
    // TODO create endpoint for every unused method from service.
}
