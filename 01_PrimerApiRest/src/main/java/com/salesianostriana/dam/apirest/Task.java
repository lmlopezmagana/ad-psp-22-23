package com.salesianostriana.dam.apirest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String text;

    public Task(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
