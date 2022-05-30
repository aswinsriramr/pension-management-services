package com.pension.processpensioninput.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pension.processpensioninput.entity.PensionDetails;
import com.pension.processpensioninput.exception.DetailsNotFoundException;
import com.pension.processpensioninput.model.PensionerDetails;
import com.pension.processpensioninput.proxy.PensionerDetailsProxy;
import com.pension.processpensioninput.repository.PensionDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessPensionInputService {
	
	private final PensionerDetailsProxy proxy;
	private final PensionDetailsRepository pensionrepo;
	
	
	
	public ResponseEntity<PensionDetails> processPension(String aadharNumber) throws DetailsNotFoundException, JsonMappingException, JsonProcessingException {
		log.info("Inside process Input service ..");
		PensionDetails pensionDetails = new PensionDetails();
		PensionerDetails pensionerDetails;
		ResponseEntity<?> response = proxy.getPensionerDetailsByAadhar(aadharNumber);
		log.info("Got Response from proxy ");
		ObjectMapper mapper = new ObjectMapper();
		if(response.getStatusCode()==HttpStatus.OK) {
			log.info("Found details using the aadhar number");
			pensionerDetails = mapper.convertValue(response.getBody(), PensionerDetails.class);
			pensionDetails.setAadharNumber(aadharNumber);
			pensionDetails.setBankServiceCharge(calculateBankServiceCharge(pensionerDetails.getBankDetails().getPublicOrPrivate()));
			pensionDetails
			.setPensionAmount(calculatePension(
					pensionerDetails.getSelfOrFamily(),pensionerDetails.getSalaryEarned(),pensionerDetails.getAllowances()));
			pensionrepo.save(pensionDetails); 
			return ResponseEntity.ok().body(pensionDetails);
		}else
			throw new DetailsNotFoundException("Details Not Found for this Aadhar Number");
	}
	
	
	public Long calculatePension(String selfOrFamily,Long salary,Long allowances ) {
		if(selfOrFamily.equals("Self"))
			return ((80*salary)/100)+allowances;
		else
			return ((50*salary)/100)+allowances;
	}
	
	public Long calculateBankServiceCharge(String publicOrPrivate ) {
		if(publicOrPrivate.equals("Public"))
			return 500L;
		else
			return 550L;
	}
}
