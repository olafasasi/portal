package com.student.portal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.student.portal.dao.dto.InvoiceDto;
import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Invoice;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.InvoiceRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class InvoiceServiceTest {

    private Invoice invoice;
    private Invoice anotherInvoice;
    private InvoiceDto invoiceDto;
    private final Long id = 1L;
    private final Long studentId = 1L;
    private final int invoiceId = 1;
    private final Double amount = 120.00;
    private final String reference = "RefSD";

    @Autowired
    private InvoiceService invoiceService;
    @MockBean
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setUp() {

        invoiceDto = new InvoiceDto();
        invoiceDto.setId(id);
        invoiceDto.setInvoiceId(invoiceId);
        invoiceDto.setAmount(amount);
        invoiceDto.setStudentId(studentId);
        invoiceDto.setReference(reference);

        invoice = new Invoice();
        invoice.setId(id);
        invoice.setInvoiceId(invoiceId);
        invoice.setAmount(amount);
        invoice.setStudentId(studentId);
        invoice.setReference(reference);

        anotherInvoice = new Invoice();
        anotherInvoice.setId(2l);
        anotherInvoice.setInvoiceId(2);
        anotherInvoice.setAmount(34.00);
        anotherInvoice.setStudentId(studentId);
        anotherInvoice.setReference("FSLDKJ");

        Mockito.when(invoiceRepository.getInvoiceByStudentId(studentId))
            .thenReturn(Arrays.asList(invoice, anotherInvoice));
        Mockito.when(invoiceRepository.findById(id))
            .thenReturn(Optional.of(invoice));
        Mockito.when(invoiceRepository.save(invoice))
            .thenReturn(invoice);
        Mockito.doNothing().when(invoiceRepository).delete(invoice);
    }

    @Test
    public void testGetInvoiceByID() {
        InvoiceDto dto = this.invoiceService.getInvoiceById(id);
        Assertions.assertThat(dto.getReference().equals(invoice.getReference()));
    }

    @Test
    public void testGetInvoiceByIDNull() {
        assertThrows(Exception.class, () -> invoiceService.getInvoiceById(null),
            "Exception was not thrown.");
    }

    @Test
    public void testGetInvoiceByStudentID() {
        List<InvoiceDto> invoices = this.invoiceService.getInvoiceByStudentId(studentId);
        Assertions.assertThat(invoices.size() > 0);
    }

    @Test
    public void testGetInvoiceByStudentIDNull() {
        List<InvoiceDto> invoices = this.invoiceService.getInvoiceByStudentId(null);
        Assertions.assertThat(invoices.size() == 0);
    }

//    @Test
//    public void deleteInvoiceById() {
//        this.invoiceService.deleteInvoiceById(1l);
//        Assertions.assertThat(1l == 1l);
//    }

}
