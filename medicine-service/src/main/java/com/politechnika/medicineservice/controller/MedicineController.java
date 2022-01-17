package com.politechnika.medicineservice.controller;

import com.politechnika.medicineservice.model.Medicine;
import com.politechnika.medicineservice.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/medicines")
@RolesAllowed({"ROLE_DOCTOR", "ROLE_ADMIN"})
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping()
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @GetMapping()
    public List<Medicine> getMedicines()  {
        return medicineRepository.findAll();
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity<Medicine> getMedicine(@PathVariable String medicineId) {
        return medicineRepository.findById(medicineId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity deleteMedicine(@PathVariable String medicineId) {
        return medicineRepository.findById(medicineId)
                .map(medicine -> { medicineRepository.delete(medicine);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity<Medicine> modifyMedicine(@PathVariable String medicineId, @RequestBody Medicine medicine) {
        return medicineRepository.findById(medicineId)
                .map(medicineFromDb -> {
                    medicineFromDb.setName(medicine.getName());
                    medicineFromDb.setContraindications(medicine.getContraindications());
                    medicineFromDb.setPrice(medicine.getPrice());
                    return ResponseEntity.ok(medicineRepository.save(medicineFromDb));
                }).orElse(ResponseEntity.notFound().build());
    }
}
