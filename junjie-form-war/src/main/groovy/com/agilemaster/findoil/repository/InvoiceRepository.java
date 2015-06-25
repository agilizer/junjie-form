package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.agilemaster.findoil.domain.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query("select distinct i from Invoice i join i.user u where u.id=? order by i.invoiceType,i.lastUpdated")
	List<Invoice> findAllByUser(Long userId);
}
