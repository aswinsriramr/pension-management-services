package com.pension.processpensioninput.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
	private Long id;
	private String bankName;
	private String accountNumber;
	private String publicOrPrivate;
}
