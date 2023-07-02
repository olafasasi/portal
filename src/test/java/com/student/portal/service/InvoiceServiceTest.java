package com.student.portal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.student.portal.dao.dto.InvoiceDto;
import com.student.portal.dao.entities.Invoice;
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

        this.invoiceDto = new InvoiceDto();
        this.invoiceDto.setId(id);
        this.invoiceDto.setInvoiceId(invoiceId);
        this.invoiceDto.setAmount(amount);
        this.invoiceDto.setStudentId(studentId);
        this.invoiceDto.setReference(reference);

        this.invoice = new Invoice();
        this.invoice.setId(id);
        this.invoice.setInvoiceId(invoiceId);
        this.invoice.setAmount(amount);
        this.invoice.setStudentId(studentId);
        this.invoice.setReference(reference);

        this.anotherInvoice = new Invoice();
        this.anotherInvoice.setId(2l);
        this.anotherInvoice.setInvoiceId(2);
        this.anotherInvoice.setAmount(34.00);
        this.anotherInvoice.setStudentId(studentId);
        this.anotherInvoice.setReference("FSLDKJ");

        Mockito.when(this.invoiceRepository.getInvoiceByStudentId(studentId))
            .thenReturn(Arrays.asList(this.invoice, this.anotherInvoice));
        Mockito.when(this.invoiceRepository.findById(id))
            .thenReturn(Optional.of(this.invoice));
        Mockito.when(this.invoiceRepository.save(this.invoice))
            .thenReturn(this.invoice);
        Mockito.doNothing().when(this.invoiceRepository).delete(this.invoice);
    }

    @Test
    public void testGetInvoiceByID() {
        InvoiceDto dto = this.invoiceService.getInvoiceById(this.id);
        Assertions.assertThat(dto.getReference().equals(this.invoice.getReference()));
    }

    @Test
    public void testGetInvoiceByIDNull() {
        assertThrows(Exception.class, () -> this.invoiceService.getInvoiceById(null),
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

}
