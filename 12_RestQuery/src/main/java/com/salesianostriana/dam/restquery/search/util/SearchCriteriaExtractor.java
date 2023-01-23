package com.salesianostriana.dam.restquery.search.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SearchCriteriaExtractor {

    static List<SearchCriteria> extractSearchCriteriaList(String search) {
        List<SearchCriteria> params = new ArrayList<>();

        //search=k1:v1,k2<v2,k3>v3
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([\\D\\S-_]+?),"); // \\[w-]+? nos permite que los valores tengan guiones medios, para las fechas
        if (!search.endsWith(",")) {
            search = search + ",";
        }
        Matcher matcher = pattern.matcher(search);

        while(matcher.find()) {
            String key = matcher.group(1); // k1:v1 => k1
            String operator = matcher.group(2); // k1:v1 => :
            Object value = matcher.group(3); // k1:v1 => v1

            params.add(new SearchCriteria(key, operator, value));

        }

        return params;
    }

}
