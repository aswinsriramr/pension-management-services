package com.pension.pensionerdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pension.pensionerdetail.entity.PensionerDetails;

@Repository
public interface PensionerDetailsRepository extends JpaRepository<PensionerDetails, Long> {

	PensionerDetails findByAadharNumber(String aadharNumber);
	
}
