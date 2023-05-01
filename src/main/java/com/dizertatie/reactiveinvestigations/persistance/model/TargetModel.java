package com.dizertatie.reactiveinvestigations.persistance.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Targets")
public class TargetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long targetId;


    //(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "targetModel")
    @OneToMany
    private List<ProductModel> products;

    public TargetModel() {
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
