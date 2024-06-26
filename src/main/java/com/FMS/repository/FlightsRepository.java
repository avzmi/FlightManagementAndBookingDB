package com.FMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FMS.model.Flights;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface FlightsRepository extends JpaRepository<Flights, Long>{
	 List<Flights> findByFromCityAndToCityAndDateAndStatus(String fromCity, String toCity, LocalDate date, String status);
	
}
