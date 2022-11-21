package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository repo;

    @GetMapping("/country/")
    public ResponseEntity<List<Country>> findAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/country/{code}")
    public ResponseEntity<Country> findByCode(
            @PathVariable String code
    ) {
        return ResponseEntity.of(repo.findFirstByCode(code));
    }

    @PostMapping("/country/")
    public ResponseEntity<Country> newCountry(@RequestBody Country country) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repo.save(country));
    }

    @PutMapping("/country/{id}")
    public ResponseEntity<Country> editCountry(@RequestBody Country country,
                                               @PathVariable Long id) {
        return ResponseEntity.of(
                repo.findById(id)
                        .map(old -> {
                            old.setCapital(country.getCapital());
                            old.setCode(country.getCode());
                            old.setName(country.getName());
                            old.setCurrency(country.getCurrency());
                            old.setPopulation(country.getPopulation());
                            return Optional.of(repo.save(old));
                        })
                        .orElse(Optional.empty())
        );
    }

        @DeleteMapping("/country/{id}")
        public ResponseEntity<?> deleteCountry (@PathVariable Long id){
            if (repo.existsById(id))
                repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }