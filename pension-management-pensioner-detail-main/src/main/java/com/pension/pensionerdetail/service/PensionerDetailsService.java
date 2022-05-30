package com.pension.pensionerdetail.service;

import org.springframework.http.ResponseEntity;

import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;



public interface PensionerDetailsService {
	public ResponseEntity<String> savePensionerDetails(PensionerDetails detail) throws AlreadyExistsException;
	public ResponseEntity<PensionerDetails> getPensionerDetailsByAadhar(String aadharNumber) throws UserNotFoundException;
}
