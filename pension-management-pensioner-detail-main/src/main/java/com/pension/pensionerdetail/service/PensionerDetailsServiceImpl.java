package com.pension.pensionerdetail.service;


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;
import com.pension.pensionerdetail.repository.PensionerDetailsRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PensionerDetailsServiceImpl implements PensionerDetailsService{

	private final PensionerDetailsRepository pensionerrepo;
	
	@Override
	public ResponseEntity<String> savePensionerDetails(PensionerDetails detail) throws AlreadyExistsException {
			log.info("Inside save details service");
			if(pensionerrepo.findByAadharNumber(detail.getAadharNumber())!=null) {
				log.info("Excpetion - Already exisits pensioner detail");
				log.info(pensionerrepo.findByAadharNumber(detail.getAadharNumber()).toString());
				throw new AlreadyExistsException("Details are already available for this Aadhar Number");
			}else {
				log.info("Pensioner is new so saving ..");
			pensionerrepo.save(detail);
			return new ResponseEntity<>("Details SucessFully Saved", HttpStatus.CREATED);
			}
		
	}

	@Override
	public ResponseEntity<PensionerDetails> getPensionerDetailsByAadhar(String aadharNumber) throws UserNotFoundException {
		log.info("Inside get details service");
		PensionerDetails details =pensionerrepo.findByAadharNumber(aadharNumber);
		if(details ==null) {
			log.info("Details not found so throwing exception");
			throw new UserNotFoundException("No Details found for this Aadhar Number");
		}else {
			log.info("Details found !! Returning the details");
			return ResponseEntity.ok().body(details);
		}
	}

}
