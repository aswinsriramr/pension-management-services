package com.pension.pensionerdetail.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.pension.pensionerdetail.entity.BankDetails;
import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;
import com.pension.pensionerdetail.repository.PensionerDetailsRepository;

@SpringBootTest
public class PensionerDetailServiceTest {

	@Mock
	PensionerDetailsRepository pensionerrepo;
	
	@InjectMocks
	PensionerDetailsServiceImpl pensionerService;
	
	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	
	PensionerDetails getPensioner() throws ParseException {
		return new PensionerDetails(
				null,"John",date.parse("29/04/1999"),"12334",10000L,2345L,"Self","456788a",new BankDetails(null,"Indian Bank","12345","Public"));
	}
	
	
	@Test
	void savePensionerDetailsTest() throws AlreadyExistsException, ParseException  {
		PensionerDetails pensioner = getPensioner();
		ResponseEntity<String> response = pensionerService.savePensionerDetails(pensioner);
		assertEquals("Details SucessFully Saved",response.getBody());
	}
	
	@Test
	void savePensionerDetailsTestFail() throws ParseException {
		PensionerDetails pensioner = getPensioner();
		when(pensionerrepo.findByAadharNumber(pensioner.getAadharNumber())).thenReturn(pensioner);
		assertThatExceptionOfType(AlreadyExistsException.class).isThrownBy(() -> {
			pensionerService.savePensionerDetails(pensioner);
		});	
	}
	
	@Test
	void getPensionerDetailsByAadharTest() throws ParseException, UserNotFoundException {
		PensionerDetails pensioner = getPensioner();
		when(pensionerrepo.findByAadharNumber(pensioner.getAadharNumber())).thenReturn(pensioner);
		ResponseEntity<PensionerDetails> response = pensionerService.getPensionerDetailsByAadhar(pensioner.getAadharNumber());
		assertEquals(pensioner,response.getBody());
	}
	
	@Test
	void getPensionerDetailsByAadharTestFail() throws ParseException, UserNotFoundException {
		PensionerDetails pensioner = getPensioner();
		when(pensionerrepo.findByAadharNumber(pensioner.getAadharNumber())).thenReturn(null);
		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
			pensionerService.getPensionerDetailsByAadhar(pensioner.getAadharNumber());
		});	
	}
	
	
	
	
	
	
}
