package org.medical.hub.repository;

import org.medical.hub.datatable.DataTableColumnSpecs;
import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.models.Medical2Ecrf1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EcrfCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EcrfCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Medical2Ecrf1> findAllWithFilter(DataTableRequest request){

        CriteriaQuery<Medical2Ecrf1> criteriaQuery = criteriaBuilder.createQuery(Medical2Ecrf1.class);

        Root<Medical2Ecrf1> ecrf1Root = criteriaQuery.from(Medical2Ecrf1.class);

        Predicate predicate = getPredicate(request, ecrf1Root);
        criteriaQuery.where(predicate);

        setOrder(request, criteriaQuery, ecrf1Root);
        TypedQuery<Medical2Ecrf1> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(request.getLength());
        typedQuery.setMaxResults(request.getLength());

        Pageable pageable = getPageable(request);

        long count = getCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    private long getCount(Predicate predicate){
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Medical2Ecrf1> countRoot = countQuery.from(Medical2Ecrf1.class);

        countQuery.select(criteriaBuilder.count(countRoot))
                .where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(DataTableRequest request){


        return PageRequest.of(request.getLength(), request.getLength());
    }

    private Predicate getPredicate(DataTableRequest request,
                                  Root<Medical2Ecrf1> ecrf1Root){

        List<Predicate> predicates = new ArrayList<>();

        for(DataTableColumnSpecs col: request.getColumns()){
            String value = col.getSearch().getValue();
            if(!Objects.equals(value, "")){
                if(col.getName().equals("patID")){
                    predicates.add(
                            criteriaBuilder.like(ecrf1Root.get("patID"), "%"+value+"%")
                    );
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(DataTableRequest request, CriteriaQuery<Medical2Ecrf1> criteriaQuery, Root<Medical2Ecrf1> ecrf1Root) {


    }
}
