package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String>, JpaSpecificationExecutor<TransactionDetail> {
}
