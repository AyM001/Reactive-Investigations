package com.dizertatie.reactiveinvestigations.persistance.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productType;
    private String firstName;
    private String lastName;
    private String phoneNr;
    private String callDuration;
    private String emailAddress;
    private String coordinates;
    private String direction;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id", referencedColumnName = "targetId")
    private TargetModel targetModel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participant_id", referencedColumnName = "participantId")
    private ParticipantModel participantModel;

    public ProductModel() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public ParticipantModel getParticipantModel() {
        return participantModel;
    }

    public void setParticipantModel(ParticipantModel participantModel) {
        this.participantModel = participantModel;
    }

    public TargetModel getTargetModel() {
        return targetModel;
    }

    public void setTargetModel(TargetModel targetModel) {
        this.targetModel = targetModel;
    }
}
