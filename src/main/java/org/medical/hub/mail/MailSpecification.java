package org.medical.hub.mail;

import org.medical.hub.repository.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class MailSpecification implements Specification<UserEmails> {

    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    private Specification<UserEmails> onlyParent() {
        return ((root, query, criteriaBuilder) -> {
//            criteriaBuilder.and(root.get())
            Join<Object, Object> parent = root.join("parent");
            Predicate equal = criteriaBuilder.equal(root.get("parentId"), 0L);
            Predicate nullable = criteriaBuilder.isNull(root.get("parentId"));
            return criteriaBuilder.and(criteriaBuilder.or(equal, nullable));
        });
    }


    @Override
    public Predicate toPredicate(Root<UserEmails> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            if (criteria.getKey().equals("parent")) {
                Predicate nullable = builder.isNull(root.get("parent"));
                predicates.add(nullable);
                continue;

            }
            switch (criteria.getOperation()) {
                case LIKE:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL:
                    if (criteria.getValue() instanceof Enum) {
                        predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    } else if (isNumeric(criteria.getValue().toString())) {

                        Predicate equal = builder.equal(root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString()));
                        Predicate nullable = builder.isNull(root.get(criteria.getKey()));

                        predicates.add(builder.or(equal, nullable));
                    } else {
//                        predicates.add(builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase()));
                    }
                    break;
//                case NOT_EQUAL:
//                    if (criteria.getValue() instanceof Enum) {
//                        predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
//                    } else if (isNumeric(criteria.getValue().toString())) {
//                        Predicate equal = builder.notEqual(root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString()));
//                        predicates.add(equal);
//                    } else {
//                        predicates.add(builder.notEqual(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase()));
//                    }
//                    break;
//                case IS_NULL:
//                    predicates.add(builder.isNull(builder.lower(root.get(criteria.getKey()))));
//                    break;
//                case GREATER_THAN:
//                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString())));
//                    break;
//                default:
//                    break;
            }
        }

//        predicates = new ArrayList<>();
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
