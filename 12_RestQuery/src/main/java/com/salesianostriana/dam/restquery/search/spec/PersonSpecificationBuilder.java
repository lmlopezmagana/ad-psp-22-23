package com.salesianostriana.dam.restquery.search.spec;

import com.salesianostriana.dam.restquery.model.Person;
import com.salesianostriana.dam.restquery.search.util.QueryableEntity;
import com.salesianostriana.dam.restquery.search.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class PersonSpecificationBuilder extends GenericSpecificationBuilder<Person> {
    public PersonSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Person.class);
    }


    /*private List<SearchCriteria> params;

    public PersonSpecificationBuilder(List<SearchCriteria> params) {
        this.params = params;
    }

    public Specification<Person> build() {

        List<SearchCriteria> checkedParams = params.stream().filter(p -> QueryableEntity.checkQueryParam(Person.class, p.getKey())).collect(Collectors.toList());
        //List<SearchCriteria> checkedParams = params.stream().filter(p -> Person.checkQueryParam(p.getKey())).collect(Collectors.toList());

        if (checkedParams.isEmpty()) {
            return null;
        }

        Specification<Person> result = new PersonSpecification(checkedParams.get(0));

        for(int i = 1; i < checkedParams.size(); i++) {
            result = result.and(new PersonSpecification(checkedParams.get(i)));
        }

        return result;


    }*/


}
