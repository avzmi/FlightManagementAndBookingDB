package com.FMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.FMS.model.Flights;
import com.FMS.model.Passengers;
import com.FMS.model.Payments;
import com.FMS.model.Reservations;
import com.FMS.model.Users;
import com.FMS.service.EmailService;
import com.FMS.service.FlightsService;
import com.FMS.service.PassengersService;
import com.FMS.service.PaymentsService;
import com.FMS.service.ReservationsService;
import com.FMS.service.UsersService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ReservationsController {

	private final ReservationsService reservationsService;
	private final PassengersService passengersService;
	private final PaymentsService paymentService;
	private final FlightsService flightsService;
	private final UsersService userService;
	private final EmailService emailService;

	@Autowired
	public ReservationsController(ReservationsService reservationsService, PassengersService passengersService,
			PaymentsService paymentService, FlightsService flightsService, UsersService userService, EmailService emailService) {
		this.reservationsService = reservationsService;
		this.passengersService = passengersService;
		this.paymentService = paymentService;
		this.flightsService = flightsService;
		this.userService = userService;
		this.emailService = emailService;
	}
	
	@PostMapping("/user/reservation/")
	public String confirmForm(@RequestParam("flightID") Long flightID, @RequestParam("passengerID") Long passengerID, @RequestParam("paymentID") Long paymentID, @RequestParam("username") String username, HttpSession session) 
	{
		Passengers passenger = passengersService.getPassengerbyID(passengerID);
		Payments payment = paymentService.getPaymentbyID(paymentID);
		Flights flight = flightsService.getFlightbyID(flightID);
		Users user = userService.getUserByUsername(username);
		Reservations reservation = new Reservations(flight, passenger, payment, user);
		session.setAttribute("username", username);
		session.setAttribute("flightID", flightID);
		session.setAttribute("passengerID", passengerID);
		reservationsService.addNewReservation(reservation);
		
        return "confirmEmail";
        
	}
	
	@GetMapping("/user/viewBookings/")
	public String viewReservations(HttpSession session) {
		String username = (String) session.getAttribute("username");
		
		if(username == null) {
			return "userLogin";
		}
		
		Users user = userService.getUserByUsername(username);
		session.setAttribute("reservations", reservationsService.getReservationsbyUser(user));
		return "ViewBookings";
	}
	
	@DeleteMapping("/user/deleteReservation/{reservationID}")
	public String deleteReservations(@PathVariable("reservationID") Long reservationID, HttpSession session) {
		
		Reservations reservation = reservationsService.getReservationbyID(reservationID);
		Flights flight = reservation.getFlight();
		if(flight.getStatus().equals("In-Flight")) {
			session.setAttribute("isDelete", 0);
			return "redirect:/user/viewBookings/";
		}
		else {
			Users user = reservation.getUser();
			reservationsService.deleteReservation(reservation.getReservationID());
			paymentService.deletePayment(reservation.getPayment().getPaymentID());
			passengersService.deletePassenger(reservation.getPassenger().getPassengerID());
			emailService.sendMessage(user.getEmail(), "Flight Cancellation", "Your Flight: "+flight.getFlightID()+" has been cancelled!");
		
		}
		
		return "redirect:/user/viewBookings/";
	}


}
