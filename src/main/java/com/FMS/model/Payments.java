package com.FMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payments {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentID;
		
	@Column(name = "amount")
	private String amount;
	        
	public Payments() {
		super();
	}

	
	public Payments(String amount) {
		super();
		this.amount = amount;
	}


	public Long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "Payments [paymentID=" + paymentID + ", amount=" + amount + "]";
	}
    
    
}
