package com.enigma.wmb_api.spesification;

import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MenuSpesification {
    public static Specification<Menu> getSpesification(SearchMenuRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (request.getName()!=null){
                Predicate predicate=cb.like(cb.lower(root.get("menuName")), "%"+request.getName().toLowerCase()+"%");
                predicates.add(predicate);
            }

            if (request.getPrice() != null){
                Predicate predicate=cb.equal(root.get("price"), request.getPrice());
                predicates.add(predicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
