package com.salesianostriana.dam.restquery.search.spec;

import com.salesianostriana.dam.restquery.model.Person;
import com.salesianostriana.dam.restquery.search.util.QueryableEntity;
import com.salesianostriana.dam.restquery.search.util.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GenericSpecificationBuilder<T> {

    private List<SearchCriteria> params;
    private Class type;

    public Specification<T> build() {
        List<SearchCriteria> checkedParams = params.stream()
                .filter(p -> QueryableEntity.checkQueryParam(type, p.getKey()))
                .collect(Collectors.toList());

        if (checkedParams.isEmpty()) {
            return null;
        }

        Specification<T> result = new GenericSpecification<T>(checkedParams.get(0));

        for(int i = 1; i < checkedParams.size(); i++) {
            result = result.and(new GenericSpecification<T>(checkedParams.get(i)));
        }

        return result;
    }



}
