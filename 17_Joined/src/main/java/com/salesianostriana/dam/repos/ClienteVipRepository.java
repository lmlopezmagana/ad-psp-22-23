package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.ClienteVip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClienteVipRepository extends JpaRepository<ClienteVip, Long> {

    List<ClienteVip> findByFechaVipBetween(LocalDate fechaVipStart, LocalDate fechaVipEnd);

}
