package com.student.portal.service;

import com.student.portal.dao.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    public InvoiceDto getInvoiceById(Long invoiceId);

    public void deleteInvoiceById(Long invoiceId);

    public List<InvoiceDto> getInvoiceByStudentId(Long studentId);
}
