package com.salesianostriana.dam.upload.post.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String image;


}
