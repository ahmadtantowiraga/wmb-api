package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {
    public static Specification<Customer> specificationCustomer(SearchCustomerRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getCustomerName()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("customerName")), "%"+request.getCustomerName().toLowerCase()+"%");
                predicates.add(predicate);
            }

            if (request.getMobilePhoneNo() != null){
                Predicate predicate=cb.like(cb.lower(root.get("mobilePhoneNo")), "%"+request.getMobilePhoneNo().toLowerCase()+"%");
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
