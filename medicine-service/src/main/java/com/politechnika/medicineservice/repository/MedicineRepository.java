package com.politechnika.medicineservice.repository;

import com.politechnika.medicineservice.model.Medicine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicineRepository extends MongoRepository<Medicine, String> {
}
