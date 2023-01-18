package com.salesianostriana.dam.restquery.search.spec;


import com.salesianostriana.dam.restquery.model.Person;
import com.salesianostriana.dam.restquery.search.util.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Log
@AllArgsConstructor
public class PersonSpecification implements Specification<Person> {

    // edad<25

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperator().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperator().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperator().equalsIgnoreCase(":")) {
            log.info(root.get(searchCriteria.getKey()).getJavaType().toString());
            if(root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                // TODO tratar tipos de datos "complicados" como booleanos, listas, ...
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%"
                );
            }
            // Esta parte es mejorable pero por ahora sirve
            else if (root.get(searchCriteria.getKey()).getJavaType().toString().equalsIgnoreCase("boolean")) {
                boolean value = searchCriteria.getValue().toString().equalsIgnoreCase("true") ? true : false;
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), value);
            }else {
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            }




        }

        return null;

    }
}
