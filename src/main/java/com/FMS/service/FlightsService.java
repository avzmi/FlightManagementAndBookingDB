package com.FMS.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FMS.model.Aircrafts;
import com.FMS.model.Airlines;
import com.FMS.model.Flights;
import com.FMS.repository.FlightsRepository;

import jakarta.transaction.Transactional;

@Service
public class FlightsService {
	
	private final FlightsRepository flightsRepository;
	
	
	public FlightsService(FlightsRepository flightsRepository) {
		this.flightsRepository = flightsRepository;
	}
	
	public List<Flights> getSearchedFlights(String toCity, String fromCity, LocalDate date, String status){
		return flightsRepository.findByFromCityAndToCityAndDateAndStatus(fromCity, toCity, date, status);
	}


	public List<Flights> getFlights(){
		return flightsRepository.findAll();
	}

	
	public Flights getFlightbyID(Long id) {
		return flightsRepository.getReferenceById(id);
		
	};


	public void addNewFlight(Flights flight) {
		flightsRepository.save(flight);
		
		
	}


	public void deleteFlight(Long id) {
		flightsRepository.deleteById(id);
	}

	@Transactional
	public void updateFlight(Long flightID, LocalDate date, String departureTime, String arrivalTime, Aircrafts aircraft,
			Airlines airline, String status, String price) {
			Flights flight = flightsRepository.findById(flightID).orElseThrow(() -> new IllegalStateException("Flight with this ID doesnot exist!"));
			if(date != null) {
				flight.setDate(date);
			}
			if(departureTime != null) {
				flight.setDepartureTime(departureTime);
			}
			if(arrivalTime != null) {
				flight.setArrivalTime(arrivalTime);
			}
			if(aircraft != null) {
				flight.setAircraft(aircraft);
			}
			if(airline != null) {
				flight.setAirline(airline);
			}
			if(status != null) {
				flight.setStatus(status);
			}
			if(price != null) {
				flight.setPrice(price);
			}
	}

}
