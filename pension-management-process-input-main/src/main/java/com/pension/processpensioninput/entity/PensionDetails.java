package com.pension.processpensioninput.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class PensionDetails {
	
	@Id
	private String aadharNumber;
	private Long pensionAmount;
	private Long BankServiceCharge;

}
