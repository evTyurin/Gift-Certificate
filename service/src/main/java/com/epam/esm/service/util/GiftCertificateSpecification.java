package com.epam.esm.service.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.repository.util.CriteriaInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

@AllArgsConstructor
public class GiftCertificateSpecification implements Specification<GiftCertificate> {

    private List<QueryCriteria> searchQueryCriteria;
    private List<QueryCriteria> orderQueryCriteria;

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for (QueryCriteria queryCriteria : searchQueryCriteria) {
            if (queryCriteria.getField().equals("tagName")) {
                predicates.add(criteriaBuilder.equal(root
                        .join("tags").get("name"), queryCriteria.getValue()));
            } else {
                predicates.add(criteriaBuilder.like(root
                                .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                        "%" + queryCriteria.getValue() + "%"));
            }
        }

        for (QueryCriteria queryCriteria : orderQueryCriteria) {
            if (queryCriteria.getValue().equals("asc")) {
                orders.add(criteriaBuilder.asc(root.get(CriteriaInfo
                        .getEntityFieldName(queryCriteria.getField()))));
            } else {
                orders.add(criteriaBuilder.desc(root.get(CriteriaInfo
                        .getEntityFieldName(queryCriteria.getField()))));
            }
        }
        criteriaQuery.orderBy(orders);
        criteriaQuery.distinct(true);

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
