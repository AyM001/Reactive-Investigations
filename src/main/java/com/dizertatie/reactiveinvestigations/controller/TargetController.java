package com.dizertatie.reactiveinvestigations.controller;

import com.dizertatie.reactiveinvestigations.common.ResourceNotFoundException;
import com.dizertatie.reactiveinvestigations.persistance.model.TargetModel;
import com.dizertatie.reactiveinvestigations.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class TargetController {

    @Autowired
    private TargetService targetService;

    @PostMapping("/addTargets")
    public void addTarget(@RequestBody TargetModel targetModel) {
        targetService.addTarget(targetModel);
    }

    @GetMapping("/targetById/{id}")
    public TargetModel getTargetById(@PathVariable Long id){
        return targetService.findTargetById(id);
    }

    @GetMapping("/targetList")
    public List<TargetModel> retrieveTargets() throws InterruptedException {
        return targetService.getTargetList();
    }

    @GetMapping(value = "/targetStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TargetModel> getTargetsReactive() throws InterruptedException {
    return targetService.getTargetsReactive();
    }

    @DeleteMapping("/deleteTarget/{id}")
    public void deleteTarget(@PathVariable Long id){
        targetService.removeTargetById(id);
    }

    //TODO check unused methods from service

}
