package com.pension.processpensioninput.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pension.processpensioninput.model.BankDetails;
import com.pension.processpensioninput.model.PensionerDetails;
import com.pension.processpensioninput.proxy.PensionerDetailsProxy;
import com.pension.processpensioninput.repository.PensionDetailsRepository;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pension.processpensioninput.entity.PensionDetails;
import com.pension.processpensioninput.exception.DetailsNotFoundException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@SpringBootTest
@Slf4j
public class ProcessPensionInputServiceTest {
	
	@InjectMocks
	ProcessPensionInputService pensionService;
	
	@Mock
	PensionerDetailsProxy proxy;
	
	@Mock
	PensionDetailsRepository pensionrepo;
	
	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	
	PensionerDetails getPensioner() throws ParseException {
		return new PensionerDetails(
				null,"John",date.parse("29/04/1999"),"12334",10000L,2345L,"Self","4567882342343a",new BankDetails(null,"Indian Bank","12345","Public"));
	}
	
	
	@Test
	void processpensionTest() throws ParseException, DetailsNotFoundException, JsonMappingException, JsonProcessingException
	{
		PensionerDetails pensioner = getPensioner();
		Mockito.<ResponseEntity<?>>when(proxy.getPensionerDetailsByAadhar(pensioner.getAadharNumber())).thenReturn(ResponseEntity.ok().body(pensioner));
		ResponseEntity<PensionDetails> response = pensionService.processPension(pensioner.getAadharNumber());
		PensionDetails pensionDetails = response.getBody();
		log.info(pensionDetails.toString());
		assertEquals(10345,pensionDetails.getPensionAmount());
		assertEquals(500,pensionDetails.getBankServiceCharge());
	}
	
	@Test
	void processpensionTestFail() throws ParseException, DetailsNotFoundException
	{
		PensionerDetails pensioner = getPensioner();
		Mockito.<ResponseEntity<?>>when(proxy.getPensionerDetailsByAadhar(pensioner.getAadharNumber())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Details found for this Aadhar Number"));
		assertThatExceptionOfType(DetailsNotFoundException.class).isThrownBy(() -> {
			pensionService.processPension(pensioner.getAadharNumber());
		});	
	}
}
