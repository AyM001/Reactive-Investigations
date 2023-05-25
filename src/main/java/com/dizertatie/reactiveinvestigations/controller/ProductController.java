package com.dizertatie.reactiveinvestigations.controller;

import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import com.dizertatie.reactiveinvestigations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /*

    TODO -> these methods will serve as a demo for development perspectives with Reactivity

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

     */

    @GetMapping("/productsById/{id}")
    public ProductModel retrieveProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping("/productsByTarget/{id}")
    public List<ProductModel> retrieveProductsOfTarget(@PathVariable Long id){
        return productService.findProductOfTarget(id);
    }

    @GetMapping("/productsByParticipant/{id}")
    public List<ProductModel> retrieveProductsOfParticipant(@PathVariable Long id){
        return productService.findProductOfParticipant(id);
    }

    @GetMapping("/productsByType/{type}")
    public List<ProductModel> retrieveProductsbyType(@PathVariable String type){
        return productService.findProductByType(type);
    }

    @GetMapping("/productsByFirstName/{firstName}")
    public List<ProductModel> retrieveProductsbyFirstName(@PathVariable String firstName){
        return productService.findProductByFirstName(firstName);
    }

    @GetMapping("/productsByLastName/{lastName}")
    public List<ProductModel> retrieveProductsbyLastName(@PathVariable String lastName){
        return productService.findProductByLastName(lastName);
    }

    @GetMapping("/productsByPhoneNr/{phoneNr}")
    public List<ProductModel> retrieveProductsbyPhoneNr(@PathVariable String phoneNr){
        return productService.findProductByPhoneNr(phoneNr);
    }

    @GetMapping("/productsByDuration/{startCAll}/{endCall}")
    public List<ProductModel> retrieveProductsbyCallDuration(@PathVariable("startCAll") String startCall, @PathVariable("endCall") String endCall){
        return productService.findProductByDuration(startCall, endCall);
    }

    @GetMapping("/allProducts")
    public List<ProductModel> getAll(){
        return productService.getAll();
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductModel productModel) {
        productService.addProduct(productModel);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id){
        productService.removeProduct(id);
    }

    @PutMapping("/updateProduct")
    public void updateProduct(@RequestBody ProductModel productModel) {
        productService.updateProduct(productModel);
    }
}
