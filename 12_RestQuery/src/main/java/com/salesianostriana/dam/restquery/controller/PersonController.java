package com.salesianostriana.dam.restquery.controller;


import com.salesianostriana.dam.restquery.model.Person;
import com.salesianostriana.dam.restquery.repos.PersonRepository;
import com.salesianostriana.dam.restquery.search.util.SearchCriteria;
import com.salesianostriana.dam.restquery.search.util.SearchCriteriaExtractor;
import com.salesianostriana.dam.restquery.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    //private final PersonRepository repository;
    private final PersonService service;

    @GetMapping("/")
    public ResponseEntity<Page<Person>> search(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        Page<Person> result = service.search(params, pageable);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

}
