package com.pension.pensionerdetail.controller;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pension.pensionerdetail.entity.BankDetails;
import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;
import com.pension.pensionerdetail.service.PensionerDetailsServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class PensionerDetailsControllerTest {
	
	
	@MockBean
	PensionerDetailsServiceImpl pensionerService;
	
	
	@Autowired
	private MockMvc mockMvc;
	
	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	
	PensionerDetails getPensioner() throws ParseException {
		return new PensionerDetails(
				null,"John",date.parse("29/04/1999"),"12334",10000L,2345L,"Self","4567882342343a",new BankDetails(null,"Indian Bank","12345","Public"));
	}
	
	
	
	
	@Test
	void getPensionerDetailsByAadharTest() throws Exception {
		PensionerDetails pensioner = getPensioner();
		when(pensionerService.getPensionerDetailsByAadhar(pensioner.getAadharNumber())).thenReturn(ResponseEntity.ok().body(pensioner));
		this.mockMvc.perform(get("/pensionerdetail/{aadharNumber}",pensioner.getAadharNumber())).andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.aadharNumber", is(pensioner.getAadharNumber())));
	}
	
	@Test
	void getPensionerDetailsByAadharTestFail() throws Exception {
		PensionerDetails pensioner = getPensioner();
		when(pensionerService.getPensionerDetailsByAadhar(pensioner.getAadharNumber())).thenThrow(UserNotFoundException.class);
		this.mockMvc.perform(get("/pensionerdetail/{aadharNumber}",pensioner.getAadharNumber()))
					.andExpect(status().isNotFound())
					.andExpect(content().string("No Details found for this Aadhar Number"));
	}
	
	@Test
	void saveDetailsTest() throws Exception {
		PensionerDetails pensioner = getPensioner();
		when(pensionerService.savePensionerDetails(pensioner)).thenReturn(new ResponseEntity<>("Details SucessFully Saved", HttpStatus.CREATED));
		this.mockMvc.perform(post("/pensionerdetail/savedetails").contentType(MediaType.APPLICATION_JSON).content(asJsonString(pensioner)))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(content().string("Details SucessFully Saved"));
	}
	
	@Test
	void saveDetailsTestFail() throws Exception {
		PensionerDetails pensioner = getPensioner();
		when(pensionerService.savePensionerDetails(pensioner)).thenThrow(AlreadyExistsException.class);
		this.mockMvc.perform(post("/pensionerdetail/savedetails").contentType(MediaType.APPLICATION_JSON).content(asJsonString(pensioner)))
					.andDo(print())
					.andExpect(status().isConflict())
					.andExpect(content().string("Details are already available for this Aadhar Number"));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
