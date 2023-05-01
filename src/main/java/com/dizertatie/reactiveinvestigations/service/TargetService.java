package com.dizertatie.reactiveinvestigations.service;

import com.dizertatie.reactiveinvestigations.common.ResourceNotFoundException;
import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import com.dizertatie.reactiveinvestigations.persistance.model.TargetModel;
import com.dizertatie.reactiveinvestigations.repository.TargetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TargetService {

    @Autowired
    private TargetRepo targetRepo;

    public Flux<TargetModel> getTargetsReactive() throws InterruptedException {
        System.out.println("Start retrieving target list reactive:");
        long start = System.currentTimeMillis();
        Flux<TargetModel> targetModelFlux = Flux.fromIterable(targetRepo.findAll())
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(targetModel -> System.out.println("Processing count: " + targetModel.getTargetId()));
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end-start) + " milliseconds.");
        return targetModelFlux;
    }

    public List<TargetModel> getTargetList() throws InterruptedException {
        System.out.println("Start retrieving target list:");
        long start = System.currentTimeMillis();
        List<TargetModel> targetsWithDelay = new ArrayList<>();
        List<TargetModel> targets = targetRepo.findAll();
        for (TargetModel trg : targets) {
            // Delay of a second to simulate a big load of objects
            Thread.sleep(1000);
            System.out.println("Processing count: " + trg.getTargetId());
            targetsWithDelay.add(trg);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end-start) + " milliseconds.");
        return targetsWithDelay;
    }

    public List<TargetModel> getTargets(){
        return targetRepo.findAll();
    }

    public TargetModel findTargetById(Long id){
        return targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found with ID: " + id));

    }

    public void addTarget(TargetModel targetModel){
        targetRepo.save(targetModel);
    }

    public void removeTargetById(Long productId){
        targetRepo.deleteById(productId);
    }
}
