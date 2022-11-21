package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Currency;

@Component
@RequiredArgsConstructor
public class InitData {

    private final CountryRepository repo;

    @PostConstruct
    public void init() {

        Country spain = new Country();
        spain.setCode("es");
        spain.setName("España");
        spain.setCapital("Madrid");
        spain.setCurrency(Currency.getInstance("EUR").getDisplayName());
        spain.setPopulation(47_326_687);

        repo.save(spain);

        Country portugal = new Country();
        portugal.setCode("pt");
        portugal.setName("Portugal");
        portugal.setCapital("Lisboa");
        portugal.setCurrency(Currency.getInstance("EUR").getDisplayName());
        portugal.setPopulation(10_298_252);

        repo.save(portugal);

        Country peru = new Country();
        peru.setCode("pe");
        peru.setName("Perú");
        peru.setCapital("Lima");
        peru.setCurrency(Currency.getInstance("PEN").getDisplayName());
        peru.setPopulation(33_738_178);

        repo.save(peru);


    }

}
