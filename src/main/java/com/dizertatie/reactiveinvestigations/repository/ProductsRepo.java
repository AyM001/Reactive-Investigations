package com.dizertatie.reactiveinvestigations.repository;

import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<ProductModel, Long> {
}
