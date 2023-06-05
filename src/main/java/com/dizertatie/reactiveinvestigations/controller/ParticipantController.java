package com.dizertatie.reactiveinvestigations.controller;


import com.dizertatie.reactiveinvestigations.persistance.model.ParticipantModel;
import com.dizertatie.reactiveinvestigations.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@CrossOrigin
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;


    @PostMapping("/addParticipant")
    public void addParticipant(ParticipantModel participantModel){
        participantService.addParticipant(participantModel);
    }

    @GetMapping("/participants")
    public List<ParticipantModel> retrieveParticipants(){
       return participantService.getAll();
    }

    @GetMapping("/retrieveParticipantsTarget/{id}")
    public List<ParticipantModel> retrieveParticipantsOfTarget(@PathVariable Long id){
        return participantService.findParticipantsOfTarget(id);
    }

    @GetMapping(value = "/participantsReactive", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ParticipantModel> retrieveParticipantsReactive() throws InterruptedException {
        return participantService.getParticipantsReactive();
    }

    @GetMapping(value = "/retrieveParticipantsOfTargetReactive/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ParticipantModel> retrieveParticipantsOfTargetReactive(@PathVariable Long id){
        return participantService.findParticipantsOfTargetReactive(id);
    }

    @DeleteMapping("/deleteParticipant/{id}")
    public void deleteParticipantById(@PathVariable Long id){
        participantService.removeParticipant(id);
    }

}
