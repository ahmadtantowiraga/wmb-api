package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.entity.Tables;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TableSpesification {
    public static Specification<Tables> getSpesification(SearchTableRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getTableName()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("tableName")), "%"+request.getTableName().toLowerCase()+"%");
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
