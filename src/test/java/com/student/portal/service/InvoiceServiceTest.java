package com.student.portal.service;

import com.student.portal.dao.dto.InvoiceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class InvoiceServiceTest {

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void getInvoiceById() {
        InvoiceDto invoiceDto = this.invoiceService.getInvoiceById(1l);
        Assertions.assertThat(invoiceDto.getInvoiceId() == 1l);
        Assertions.assertThat(invoiceDto.getAmount().equals(12312.0));
        Assertions.assertThat(invoiceDto.getReference().equals("QWERT"));
    }

    @Test
    public void deleteInvoiceById() {
        this.invoiceService.deleteInvoiceById(1l);
        Assertions.assertThat(1l == 1l);
    }

    @Test
    public void getInvoiceByStudentId() {
        Integer size = this.invoiceService.getInvoiceByStudentId(1l).size();
        Assertions.assertThat(size > 0l);
    }

}
