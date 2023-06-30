package com.student.portal.service.impl;

import com.student.portal.dao.dto.InvoiceDto;
import com.student.portal.dao.entities.Invoice;
import com.student.portal.dao.repository.InvoiceRepository;
import com.student.portal.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDto getInvoiceById(Long invoiceId) {
        Invoice invoice = this.invoiceRepository.findById(invoiceId).get();
        return this.mapInvoiceToInvoiceDto(invoice);
    }

    @Override
    public void deleteInvoiceById(Long invoiceId) {
        this.invoiceRepository.deleteById(invoiceId);
    }

    @Override
    public List<InvoiceDto> getInvoiceByStudentId(Long studentId) {
        return this.invoiceRepository.getInvoiceByStudentId(studentId).stream()
        .map(invoice -> mapInvoiceToInvoiceDto(invoice)).collect(Collectors.toList());
    }

    private InvoiceDto mapInvoiceToInvoiceDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId(invoice.getId());
        invoiceDto.setInvoiceId(invoice.getInvoiceId());
        invoiceDto.setAmount(invoice.getAmount());
        invoiceDto.setReference(invoice.getReference());
        invoiceDto.setStudentId(invoice.getStudentId());
        return invoiceDto;
    }


}
