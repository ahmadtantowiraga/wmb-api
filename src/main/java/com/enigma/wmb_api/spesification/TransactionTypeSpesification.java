package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.TransactionType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionTypeSpesification {
    public static Specification<TransactionType> getSpesification(SearchTransactionTypeRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getDescription()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("description")), "%"+request.getDescription().toLowerCase()+"%");
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
