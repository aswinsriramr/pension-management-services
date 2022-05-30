package com.pension.processpensioninput.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pension.processpensioninput.entity.PensionDetails;

public interface PensionDetailsRepository extends JpaRepository<PensionDetails, Long> {

}
