package com.politechnika.medicineservice.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.politechnika.medicineservice.model.Medicine;
import com.politechnika.medicineservice.model.Prescription;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@Service
public class PdfServiceImpl implements  PdfService{
    public void generatePdf(List<Prescription> prescriptions, HttpServletResponse response){
        try{
            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=prescriptions.pdf");
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();
            PdfPTable table = new PdfPTable(2);
      //      pdf.add(new Paragraph("Pay for visit №" + visit.getId()));
            if(prescriptions.size()>0) {
                for (Prescription prescription : prescriptions) {

                    pdf.add(new Paragraph(Chunk.NEWLINE));

                    table.addCell("Doctor email");
                    table.addCell(prescription.getDoctorEmail());
                    table.addCell("Patient email");
                    table.addCell(prescription.getPatientEmail());
                    table.addCell("Date");
                    table.addCell(String.valueOf(prescription.getDate()).substring(0, 10));
                    for (Medicine medicine : prescription.getMedicines()) {
                        table.addCell("Medicine:");
                        table.addCell(medicine.getName());
                    }


                }
            }
            else {
                pdf.add(new Paragraph(Chunk.NEWLINE));
                table.resetColumnCount(1);
                table.addCell("No prescriptions for you");

            }
            pdf.add(table);
            pdf.close();
            o.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
