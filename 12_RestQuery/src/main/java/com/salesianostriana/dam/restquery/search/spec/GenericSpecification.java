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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Log
@AllArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        Class type = root.get(searchCriteria.getKey()).getJavaType();
        String key = searchCriteria.getKey();
        String operator = searchCriteria.getOperator();
        Object value = searchCriteria.getValue();

        if (operator.equalsIgnoreCase(">")) {

            if (isTemporal(type)) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key),
                        getTemporalValue(value, type));
            }
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String>get(key), value.toString());
        } else if (operator.equalsIgnoreCase("<")) {
            //if (Arrays.stream(root.get(searchCriteria.getKey()).getJavaType().getInterfaces()).anyMatch(c -> c.getName() == "java.time.temporal.Temporal")) {
            if (isTemporal(type)) {
                /*Comparable value = null;
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                    // Patrón por defecto: yyyy-mm-dd
                    value = LocalDate.parse(searchCriteria.getValue().toString());
                } else if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class) {
                    // Patrón por defecto: yyyy-mm-dd hh24:mi:ss -- NO VALE POR LOS :. Hay que adaptarlo. Por ejemplo yyyy-MM-dd hh24_mi_ss
                    String pattern = "yyyy-MM-dd HH_mm_ss";
                    value = LocalDateTime.parse(searchCriteria.getValue().toString(), DateTimeFormatter.ofPattern(pattern));
                    log.info(value.toString());
                }*/


                return criteriaBuilder.lessThanOrEqualTo(root.get(key),
                        getTemporalValue(value, type));
            }
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String>get(key), value.toString());
        } else if (operator.equalsIgnoreCase(":")) {
            if(isString(type)) {
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%"
                );
            }
            // Esta parte sigue siendo mejorable pero ahora está mejor :)
            else if (isBoolean(type)) {
                //boolean value = searchCriteria.getValue().toString().equalsIgnoreCase("true") ? true : false; // Se podría adaptar a que se pase 0 o 1 en el query param
                if (isValidBooleanValue(value.toString()))
                      return criteriaBuilder.equal(root.get(key), getBooleanValue(value.toString()));
                else
                    return null;
            } else if (isTemporal(type)) {
                return criteriaBuilder.equal(root.get(key), getTemporalValue(value, type));
            }else {
                return criteriaBuilder.equal(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }

        return null;

    }

    private boolean isBoolean(Class clazz) {
        return clazz.getName().toLowerCase().contains("boolean");
    }

    private boolean isTemporal(Class clazz) {
        return Arrays.stream(clazz.getInterfaces()).anyMatch(c -> c.getName() == "java.time.temporal.Temporal");
    }

    private boolean isLocalDate(Class clazz) {
        return clazz == LocalDate.class;
    }

    private boolean isLocalDateTime(Class clazz) {
        return clazz == LocalDateTime.class;
    }

    private boolean isLocalTime(Class clazz) {
        return clazz == LocalTime.class;
    }

    private boolean isString(Class clazz) {
        return clazz == String.class;
    }

    private Comparable getTemporalValue(Object value, Class clazz) {
        Comparable result;

        if (isLocalDate(clazz)) {
            // Patrón por defecto: yyyy-mm-dd
            result = LocalDate.parse(value.toString());
        } else if (isLocalDateTime(clazz)) {
            // Patrón por defecto: yyyy-mm-dd hh24:mi:ss -- NO VALE POR LOS :. Hay que adaptarlo. Por ejemplo yyyy-MM-dd hh24_mi_ss
            String pattern = "yyyy-MM-dd HH_mm_ss";
            result = LocalDateTime.parse(value.toString(), DateTimeFormatter.ofPattern(pattern));
        } else if (isLocalTime(clazz)) {
            // Patrón por defecto: hh24:mi:ss -- NO VALE POR LOS :. Hay que adaptarlo. Por ejemplo hh24_mi_ss
            String pattern = "HH_mm_ss";
            result = LocalTime.parse(value.toString(), DateTimeFormatter.ofPattern(pattern));
        } else
            result = null;

        return result;
    }

    private boolean getBooleanValue(String value) {
        return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1"));

    }

    private boolean isValidBooleanValue(String value) {
        return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1")
                || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0")
                );
    }

}
