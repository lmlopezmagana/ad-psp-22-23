package com.salesianostriana.dam.search;

import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AlumnoSpecificationBuilder {

    private final List<SearchCriteria> params = new ArrayList<>();

    public AlumnoSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Alumno> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(AlumnoSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);


        // La condición final es un AND de todas las condiciones
        for(int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }

}
