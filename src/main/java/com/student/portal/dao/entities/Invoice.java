package com.student.portal.dao.entities;

import com.student.portal.dao.dto.Status;
import javax.persistence.*;


@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "reference")
    private String reference;

    private Status status;

    public Invoice() {
    }

    public Invoice(int invoiceId, Long studentId) {
        this.invoiceId = invoiceId;
        this.studentId = studentId;
    }

    public Invoice(Long id, int invoiceId,
        Long studentId, Double amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.studentId = studentId;
        this.amount = amount;
    }

    public Invoice(Long id, int invoiceId, Long studentId,
        Double amount, String reference) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.studentId = studentId;
        this.amount = amount;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
