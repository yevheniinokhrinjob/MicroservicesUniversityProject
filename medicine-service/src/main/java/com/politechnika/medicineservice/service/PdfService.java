package com.politechnika.medicineservice.service;



import com.politechnika.medicineservice.model.Prescription;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PdfService {
    public void generatePdf(List<Prescription> prescriptions, HttpServletResponse response);
}
