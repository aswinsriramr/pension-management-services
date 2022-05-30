package com.pension.pensionerdetail.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PensionerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private Date dateOfBirth;
	private String PAN;
	private Long salaryEarned;
	private Long allowances;
	private String selfOrFamily;
	private String aadharNumber;
	@OneToOne(cascade = {CascadeType.ALL})
	private BankDetails bankDetails;

}
