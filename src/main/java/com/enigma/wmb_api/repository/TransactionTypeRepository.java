package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, TransactionTypeID>, JpaSpecificationExecutor<TransactionType> {
}
