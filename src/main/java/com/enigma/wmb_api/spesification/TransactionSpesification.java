package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

public class TransactionSpesification {
    public static Specification<Transaction> getSpesification(SearchTransactionRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getCustomerId()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("customer")), "%"+request.getCustomerId().toLowerCase()+"%");
                predicates.add(predicate);
            }
            if (request.getTableId()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("tables")), "%"+request.getTableId().toLowerCase()+"%");
                predicates.add(predicate);
            }
            if (request.getTransactionTypeId()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("transactionType")), "%"+request.getTransactionTypeId().name().toLowerCase()+"%");
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
