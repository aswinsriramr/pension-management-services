package com.pension.processpensioninput.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PensionerDetails {

	private Long id;
	private String name;
	private Date dateOfBirth;
	private String PAN;
	private Long salaryEarned;
	private Long Allowances;
	private String selfOrFamily;
	private String aadharNumber;
	private BankDetails bankDetails;
}
