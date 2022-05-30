package com.pension.pensionerdetail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;
import com.pension.pensionerdetail.service.PensionerDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pensionerdetail")
@Slf4j
public class PensionerDetailsController {
	
	private final PensionerDetailsServiceImpl pensionService;
	
	@GetMapping("/{aadharNumber}")
	public ResponseEntity<PensionerDetails> 
					getPensionerDetailsByAadhar(@PathVariable("aadharNumber") String aadharNumber) throws UserNotFoundException {
		log.info("Inside get details by aadhar controller");
		return pensionService.getPensionerDetailsByAadhar(aadharNumber);
	}
	
	
	@PostMapping("/savedetails")
	public ResponseEntity<String> saveDetails(@RequestBody PensionerDetails pensionerDetail) throws AlreadyExistsException {
		log.info("Inside save ddetails controller");
		return pensionService.savePensionerDetails(pensionerDetail);
	}
	

}
