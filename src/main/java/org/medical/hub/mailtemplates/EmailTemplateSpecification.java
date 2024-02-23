package org.medical.hub.mailtemplates;

import org.medical.hub.repository.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EmailTemplateSpecification implements Specification<EmailTemplate> {

    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }


    @Override
    public Predicate toPredicate(Root<EmailTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
