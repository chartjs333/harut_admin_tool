package org.medical.hub.repository;

import lombok.Data;
import org.medical.hub.models.Medical2Ecrf1;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class RuleSpecification implements Specification<Medical2Ecrf1> {

    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }


    @Override
    public Predicate toPredicate(Root<Medical2Ecrf1> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            switch (criteria.getOperation()) {
                case LIKE:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL:
                    predicates.add(builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase()));
                    break;
                default:
                    break;
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}