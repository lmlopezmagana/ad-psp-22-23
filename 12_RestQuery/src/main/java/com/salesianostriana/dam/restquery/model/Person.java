package com.salesianostriana.dam.restquery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Person {

    @Id @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private LocalDate hireDate;
    private boolean isMarried;
    private String gender;

}
