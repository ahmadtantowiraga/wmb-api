package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.Transaction_detail_request.SearchTransactionDetailRequest;
import com.enigma.wmb_api.entity.TransactionDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailDetailSpesification {
    public static Specification<TransactionDetail> getSpesification(SearchTransactionDetailRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getTransactionId()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("transaction")), "%"+request.getTransactionId().toLowerCase()+"%");
                predicates.add(predicate);
            }
            if (request.getMenuId()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("tables")), "%"+request.getMenuId().toLowerCase()+"%");
                predicates.add(predicate);
            }
            if (request.getQty()!=null){
                Predicate predicate=cb.equal(root.get("qty"), request.getQty());
                predicates.add(predicate);
            }
            if (request.getPrice()!=null){
                Predicate predicate=cb.equal(root.get("price"), request.getPrice());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
