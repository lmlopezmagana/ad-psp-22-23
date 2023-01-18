package com.salesianostriana.dam.restquery.repos;

import com.salesianostriana.dam.restquery.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository extends JpaRepository<Person, Long>,
        JpaSpecificationExecutor<Person> {
}
