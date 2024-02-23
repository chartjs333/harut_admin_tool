package org.medical.hub.workflow;

import org.medical.hub.mailtemplates.EmailTemplate;
import org.medical.hub.repository.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class WorkflowSpecification implements Specification<Workflow> {

    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }


    @Override
    public Predicate toPredicate(Root<Workflow> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
