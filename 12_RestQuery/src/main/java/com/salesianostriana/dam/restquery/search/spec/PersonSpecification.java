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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Arrays;

@Log
@AllArgsConstructor
public class PersonSpecification implements Specification<Person> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperator().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperator().equalsIgnoreCase("<")) {
            if (Arrays.stream(root.get(searchCriteria.getKey()).getJavaType().getInterfaces()).anyMatch(c -> c.getName() == "java.time.temporal.Temporal")) {
                Comparable value = null;
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                    // Patrón por defecto: yyyy-mm-dd
                    value = LocalDate.parse(searchCriteria.getValue().toString());
                } else if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class) {
                    // Patrón por defecto: yyyy-mm-dd hh24:mi:ss -- NO VALE POR LOS :. Hay que pensar otro patrón y adaptarlo. Por ejemplo yyyy-MM-dd hh24_mi_ss
                    //LocalDateTime value;
                    String pattern = "yyyy-MM-dd HH_mm_ss";
                    value = LocalDateTime.parse(searchCriteria.getValue().toString(), DateTimeFormatter.ofPattern(pattern));
                    log.info(value.toString());
                }

                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), value);
            }
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperator().equalsIgnoreCase(":")) {
            // TODO tratar tipos de datos "complicados" como booleanos HECHO, ¿listas?, ...
            if(root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%"
                );
            }
            // Esta parte es mejorable pero por ahora sirve
            else if (root.get(searchCriteria.getKey()).getJavaType().toString().equalsIgnoreCase("boolean")) {
                boolean value = searchCriteria.getValue().toString().equalsIgnoreCase("true") ? true : false; // Se podría adaptar a que se pase 0 o 1 en el query param
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), value);
            }else {
                return criteriaBuilder.equal(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }

        return null;

    }
}
