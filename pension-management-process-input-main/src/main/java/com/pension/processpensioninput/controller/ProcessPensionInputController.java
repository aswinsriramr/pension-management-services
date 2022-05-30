package com.pension.processpensioninput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pension.processpensioninput.entity.PensionDetails;
import com.pension.processpensioninput.exception.DetailsNotFoundException;
import com.pension.processpensioninput.proxy.AuthorizationContext;
import com.pension.processpensioninput.service.ProcessPensionInputService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pensiondetail")
public class ProcessPensionInputController {
	@Autowired
	private ProcessPensionInputService processpensionService;
	
	@Autowired
	AuthorizationContext authorizationContext;
	
	
	@PostMapping("/")
	public ResponseEntity<PensionDetails> ProcessPensionInput
						(@RequestParam String aadharNumber,@RequestHeader(value = "Authorization") String authorization) throws DetailsNotFoundException, JsonMappingException, JsonProcessingException {
		log.info("Inside process input controller");
		authorizationContext.setAuthorization(authorization);
		return processpensionService.processPension(aadharNumber);
	}
}
