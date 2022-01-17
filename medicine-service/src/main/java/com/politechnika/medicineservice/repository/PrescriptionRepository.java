package com.politechnika.medicineservice.repository;

import com.politechnika.medicineservice.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
}
