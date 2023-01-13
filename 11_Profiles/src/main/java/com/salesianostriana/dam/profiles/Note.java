package com.salesianostriana.dam.profiles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Note {

    @Id //@GeneratedValue
    private Long id;

    private String title, text;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
