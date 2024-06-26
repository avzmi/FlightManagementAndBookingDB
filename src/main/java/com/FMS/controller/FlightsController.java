package com.FMS.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.FMS.model.Aircrafts;
import com.FMS.model.Airlines;
import com.FMS.model.Flights;
import com.FMS.model.Passengers;
import com.FMS.model.Reservations;
import com.FMS.model.Users;
import com.FMS.service.AircraftsService;
import com.FMS.service.AirlinesService;
import com.FMS.service.EmailService;
import com.FMS.service.FlightsService;
import com.FMS.service.ReservationsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping()
public class FlightsController {
	private final FlightsService flightsService;
	private final AircraftsService aircraftsService;
	private final AirlinesService airlinesService;
	private final EmailService emailService;
	private final ReservationsService reservationsService;
    @GetMapping("/flights")
    public String showFlightsMain(HttpSession session) {
    	Long val = (long) 0;
    	session.setAttribute("flightID", val);
        return "flightsMain"; 
    }

	
	public FlightsController(FlightsService flightsService, AircraftsService aircraftsService,
			AirlinesService airlinesService, EmailService emailService, ReservationsService reservationsService) {
		super();
		this.flightsService = flightsService;
		this.aircraftsService = aircraftsService;
		this.airlinesService = airlinesService;
		this.emailService = emailService;
		this.reservationsService = reservationsService;
	}


	@GetMapping("/flights/flightform")
    public String showFlightForm() {
        return "flightForm"; 
    }
	
	@RequestMapping("/flights/update/")
	public String updateFlights(@RequestParam("flightID") Long flightID, HttpSession session) {
		session.setAttribute("aircrafts", aircraftsService.getAllAircrafts());
		session.setAttribute("flightID", flightID);
		return "aircraftsSelect";
	}
		
//    @PostMapping("/flights/flightform/add")
//    public String handleFlightFormSubmission(Flights flight, HttpSession session) {
//        flightsService.addNewFlight(flight);
//        showAllFlightsData(session);
//        return "redirect:/flights/getFlights";
//    }
    
	@GetMapping("/flights/getFlights")
	public String showAllFlightsData(HttpSession session){
		session.setAttribute("flights", flightsService.getFlights());
		return "flightData";
	}

		
	@RequestMapping("/flights/flightform/add")
	public String registerNewFlights(@RequestParam("airlineID") Long airlineID,@RequestParam("aircraftID") Long aircraftID, @RequestParam("date") LocalDate date, @RequestParam("departureTime") String departureTime, @RequestParam("arrivalTime") String arrivalTime, @RequestParam("toCity") String toCity, @RequestParam("fromCity") String fromCity,  @RequestParam("price") String price, @RequestParam("status") String status,HttpSession session) {
		Aircrafts aircraft = aircraftsService.getAircraftByID(aircraftID);
		Airlines airline = airlinesService.getAirlineByID(airlineID);
		System.out.println(airline);
		System.out.println(aircraft);
		Flights flight = new Flights(date,departureTime,arrivalTime,toCity,fromCity,aircraft,airline,price,status);
		flightsService.addNewFlight(flight);
		showAllFlightsData(session);
		return "redirect:/flights/getFlights";
	};
	
	@DeleteMapping("/flights/delete/{flightid}")
	public String deleteFlights(@PathVariable("flightid") Long id, HttpSession session) {
		
		flightsService.deleteFlight(id);
		showAllFlightsData(session);
		return "redirect:/flights/getFlights";
	};
	
    @GetMapping("/flights/update/{flightid}")
    public String showEditForm(@PathVariable("flightid") Long id, HttpSession session)
    {
        Flights flight = flightsService.getFlightbyID(id);
        session.setAttribute("flight", flight);
        return "editFlight";
    }

    @PutMapping(path = "/flights/flightform/update")
    public String updateFlight(
    		@RequestParam("flightID") Long id,
            @RequestParam("date") LocalDate date,
            @RequestParam("departureTime") String departureTime,
            @RequestParam("arrivalTime") String arrivalTime,
            @RequestParam("price") String price,
            @RequestParam("status") String status,
            @RequestParam("airlineID") Long airlineID,
            @RequestParam("aircraftID") Long aircraftID
    ) {
    	
        flightsService.updateFlight(id, date, departureTime, arrivalTime, aircraftsService.getAircraftByID(aircraftID), airlinesService.getAirlineByID(airlineID), status, price);
        List<Reservations> R = reservationsService.getReservationsbyFlight(flightsService.getFlightbyID(id));
        if(R != null) {
        for(int i = 0 ; i< R.size() ; i++) {
        	Passengers Passenger = R.get(i).getPassenger();
        	Users user = R.get(i).getUser();
        	emailService.sendMessage(user.getEmail(), "Change in Flight Details", "There has been a change in your flight. The new flight details are as follows:\n"
        			+ "Flight ID: "+id+"\n"+"Passenger ID: "+Passenger.getPassengerID()+"\n"
        			+"Date: "+date+"\n"+"Departure Time: "+departureTime+"\n"+"Arrival Time: "+arrivalTime+"\n"
        			+"Price: "+price+"\n"+"Status: "+status+"\n"+"Airline ID: "+airlineID+"\n"+"Aircraft ID: "+aircraftID);
        }
        }
        return "redirect:/flights/getFlights";
    }
	
    @PostMapping("flights/getSearchedFlight/")
    public String getSearchedFlight(@RequestParam("toCity") String toCity, @RequestParam("fromCity") String fromCity, @RequestParam("date") LocalDate date, HttpSession session) {
        String username = (String)session.getAttribute("username");
        if(username == null) {
        	return "userLogin";
        }
    	List<Flights> flight = flightsService.getSearchedFlights(toCity, fromCity, date, "Scheduled");
    	session.setAttribute("flights", flight);
    	session.setAttribute("username", username);
        return "searchFlight"; 
    }
    
	@GetMapping("/user/flights/")
	public String searchFlightsPage( HttpSession session) {
		
		String username = (String)session.getAttribute("username");
		
		if(username == null) {
			return "userLogin";
		}
		
		session.setAttribute("username", username);
		return "searchFlight";
	}
	
	@GetMapping("/flights/selectAircrafts")
	public String getAircraftsData(HttpSession session) {
		session.setAttribute("aircrafts", aircraftsService.getAllAircrafts());
		return "aircraftsSelect";
		
	}
	
	@PostMapping("/flights/airlinesSelect/")
	public String selectAircrafts(@RequestParam("aircraftID") Long aircraftID ,HttpSession session) {
		session.setAttribute("aircraftID", aircraftID);
		System.out.println(aircraftID);
		session.setAttribute("airlines", airlinesService.getAllAirlines());
		return "airlineSelect";
	}

    @RequestMapping("/flights/selectFlights/")
    public String showAllAirlines(@RequestParam("flightID") Long flightID,@RequestParam("airlineID") Long airlineID,@RequestParam("aircraftID")Long aircraftID,HttpSession session) {
        
    	session.setAttribute("airlineID", airlineID);
        session.setAttribute("aircraftID", aircraftID);
        System.out.println(airlineID);
        System.out.println(aircraftID);
        if((Long)session.getAttribute("flightID") != 0) {
            session.setAttribute("flightID", flightID);
        	
        	return "editFlight";
        }        
        return "flightForm";
    }


}
