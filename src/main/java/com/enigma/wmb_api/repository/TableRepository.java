package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TableRepository extends JpaRepository<Tables, String>, JpaSpecificationExecutor<Tables> {
}
