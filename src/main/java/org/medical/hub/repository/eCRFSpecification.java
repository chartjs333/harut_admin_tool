package org.medical.hub.repository;

import lombok.Data;
import org.medical.hub.models.Medical2Ecrf1;
import org.medical.hub.models.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class eCRFSpecification implements Specification<Medical2Ecrf1> {

    private List<SearchCriteria> list = new ArrayList<>();
    private List<String> patIds = new ArrayList<>();
    private List<String> userIds = new ArrayList<>();

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
                default:break;
            }
        }

        if (!patIds.isEmpty()) {

            predicates.add(builder.or(patIds.stream()
                    .map(patId -> builder.equal(builder.lower(root.get("patID")), patId.toLowerCase())).toArray(Predicate[]::new)));
        }

        if (!userIds.isEmpty()) {
            Join<Medical2Ecrf1, User> user = root.join("user");
            predicates.add(builder.or(userIds.stream()
                    .map(id-> builder.equal(user.get("id"), id))
                    .toArray(Predicate[]::new)));
        }

        return builder.and(predicates.toArray(new Predicate[0]));

    }
}
