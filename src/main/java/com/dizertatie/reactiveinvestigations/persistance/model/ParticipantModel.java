package com.dizertatie.reactiveinvestigations.persistance.model;

import javax.persistence.*;

@Entity
@Table(name = "participants")
public class ParticipantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;
    private String firstName;
    private String lastName;
    private String phoneNr;

    public ParticipantModel() {
    }

    public ParticipantModel(String firstName, String lastName, String phoneNr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public long getParticipantId() {
        return participantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

}
