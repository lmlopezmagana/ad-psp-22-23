package com.salesianostriana.dam.restquery.search.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.restquery.model.Person;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface QueryableEntity {

    static boolean checkQueryParam(Class clazz, String fieldName) {

        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .anyMatch(n -> n.equalsIgnoreCase(fieldName));
        //return Arrays.stream(getClass().getQueryFields()).anyMatch(n -> n.equalsIgnoreCase(fieldName));
    }


}
