package com.dizertatie.reactiveinvestigations.service;

import com.dizertatie.reactiveinvestigations.common.ResourceNotFoundException;
import com.dizertatie.reactiveinvestigations.persistance.model.ParticipantModel;
import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import com.dizertatie.reactiveinvestigations.persistance.model.TargetModel;
import com.dizertatie.reactiveinvestigations.repository.ParticipantsRepo;
import com.dizertatie.reactiveinvestigations.repository.ProductsRepo;
import com.dizertatie.reactiveinvestigations.repository.TargetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantsRepo participantsRepo;
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private TargetRepo targetRepo;

    public Flux<ParticipantModel> getParticipantsReactive() throws InterruptedException {
        System.out.println("Start retrieving participants list reactive:");
        long start = System.currentTimeMillis();
        Flux<ParticipantModel> participantModelFlux = Flux.fromIterable(participantsRepo.findAll())
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(participantModel -> System.out.println("Processing count: " + participantModel.getParticipantId()));
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end-start) + " milliseconds.");
        return participantModelFlux;
    }

    public List<ProductModel> getProductList() throws InterruptedException {
        System.out.println("Start retrieving product list:");
        long start = System.currentTimeMillis();
        List<ProductModel> productsWithDelay = new ArrayList<>();
        List<ProductModel> products = productsRepo.findAll();
        for (ProductModel prd : products) {
            // Delay of a second to simulate a big load of objects
            Thread.sleep(1000);
            System.out.println("Processing count: " + prd.getProductId());
            productsWithDelay.add(prd);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end-start) + " milliseconds.");
        return productsWithDelay;
    }

    public List<ParticipantModel> getAll(){
        return participantsRepo.findAll();
    }

    public Flux<ParticipantModel> findParticipantsOfTargetReactive(Long id){
        TargetModel targetModel = targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found with ID: " + id));
        List<ProductModel> allProducts = productsRepo.findAll();
        List<ProductModel> productsOfTarget = allProducts.stream().filter(productModel -> productModel.getTargetModel().getTargetId().equals(targetModel.getTargetId())).collect(Collectors.toList());
        List<ParticipantModel> participantsOfTarget = productsOfTarget.stream().map(ProductModel::getParticipantModel).collect(Collectors.toList());
        return Flux.fromIterable(participantsOfTarget)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(participantModel -> System.out.println("Processing count: " + participantModel.getParticipantId()));
    }

    public List<ParticipantModel> findParticipantsOfTarget(Long id){
        TargetModel targetModel = targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found with ID: " + id));
        List<ProductModel> allProducts = productsRepo.findAll();
        List<ProductModel> productsOfTarget = allProducts.stream().filter(productModel -> productModel.getTargetModel().getTargetId().equals(targetModel.getTargetId())).collect(Collectors.toList());
        return productsOfTarget.stream().map(ProductModel::getParticipantModel).collect(Collectors.toList());
    }

    public Optional<ParticipantModel> findProductById(Long id){
        return participantsRepo.findById(id);
    }

    public void addParticipant(ParticipantModel participantModel){
        participantsRepo.save(participantModel);
    }

    public ParticipantModel generateParticipant(){
        return new ParticipantModel("Nicu","Gheara","0722112112");
    }

    public void removeParticipant(Long participantId){
        participantsRepo.deleteById(participantId);
    }
}
