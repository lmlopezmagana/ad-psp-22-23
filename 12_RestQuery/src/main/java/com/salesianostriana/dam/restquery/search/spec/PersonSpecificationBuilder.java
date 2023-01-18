package com.salesianostriana.dam.restquery.search.spec;

import com.salesianostriana.dam.restquery.model.Person;
import com.salesianostriana.dam.restquery.search.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PersonSpecificationBuilder {

    private List<SearchCriteria> params;

    public PersonSpecificationBuilder(List<SearchCriteria> params) {
        this.params = params;
    }

    public Specification<Person> build() {
    //public PersonSpecification build() {

        if (params.isEmpty()) {
            return null;
        }

        Specification<Person> result = new PersonSpecification(params.get(0));

        for(int i = 1; i < params.size(); i++) {
            result = result.and(new PersonSpecification(params.get(i)));
        }

        return result;


    }


}
