package com.student.portal.dao.repository;

import com.student.portal.dao.dto.Status;
import com.student.portal.dao.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public List<Invoice> getInvoiceByStudentId(Long id);

    public List<Invoice> getInvoiceByStudentIdAndStatus(Long id, Status status);
}
