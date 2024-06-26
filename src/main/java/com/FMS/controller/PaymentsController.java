package com.FMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FMS.model.Payments;
import com.FMS.service.PaymentsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentsController {

	private final PaymentsService paymentService;
	
	@Autowired
	public PaymentsController(PaymentsService paymentService) {
		this.paymentService = paymentService;
	}
	
	
	@PostMapping("/user/paymentForm/")
	public String addPayment(@RequestParam("flightID") Long flightID,@RequestParam("passengerID") Long passengerID,@RequestParam("username") String username, @RequestParam("price") String price,HttpSession session ) {
		Payments payment = new Payments(price);
		paymentService.addNewPayment(payment);
        session.setAttribute("flightID", flightID);
        session.setAttribute("passengerID", passengerID);
        session.setAttribute("paymentID", payment.getPaymentID());
        session.setAttribute("username", username);
        
		return "confirmReservation";
	}



}
