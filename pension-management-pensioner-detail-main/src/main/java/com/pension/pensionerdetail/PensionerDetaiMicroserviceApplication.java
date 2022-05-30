package com.pension.pensionerdetail;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.pension.pensionerdetail.entity.BankDetails;
import com.pension.pensionerdetail.entity.PensionerDetails;
import com.pension.pensionerdetail.service.PensionerDetailsServiceImpl;

@SpringBootApplication
@EnableFeignClients
public class PensionerDetaiMicroserviceApplication {
	
	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	


	public static void main(String[] args) {
		SpringApplication.run(PensionerDetaiMicroserviceApplication.class, args);
	}
	
	
	@Bean
	CommandLineRunner run(PensionerDetailsServiceImpl pensionservice) {
		
		return args -> {
			
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"John",date.parse("29/04/1999"),"1234567890",1003400L,2445L,"Self","123456789012",new BankDetails(null,"Indian Bank","123456789012345","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Joe",date.parse("28/05/1998"),"1235467890",1023400L,2345L,"Family","456785345678",new BankDetails(null,"State Bank","123460987654321","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Job",date.parse("27/03/1997"),"1236409876",100230L,2325L,"Self","456784098765",new BankDetails(null,"ICICI Bank","12347112509876","Private")));
			/*pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Toast",date.parse("22/04/1990"),"1234567891",19000L,323445L,"Family","123996789012",new BankDetails(null,"Indian Bank","563456789012345","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Abe",date.parse("28/05/1998"),"1235467892",100400L,2345L,"Self","456785345645",new BankDetails(null,"State Bank","123460237654321","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Jack",date.parse("27/03/1997"),"1236409873",105400L,4235L,"Family","456784098765",new BankDetails(null,"ICICI Bank","123891234509876","Private")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Jim",date.parse("29/04/1999"),"1234567894",100030L,4345L,"Self","123456789012",new BankDetails(null,"Indian Bank","123452189012345","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Pam",date.parse("28/05/1998"),"1235467895",100030L,42345L,"Family","456785345612",new BankDetails(null,"State Bank","903460987654321","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Bob",date.parse("27/03/1997"),"1236409877",10002340L,42345L,"Family","456784080765",new BankDetails(null,"ICICI Bank","803471234509876","Private")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Peter",date.parse("29/04/1999"),"1234567898",1002400L,3245L,"Self","12377789012",new BankDetails(null,"Indian Bank","603456789012345","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Logan",date.parse("28/05/1998"),"1235467899",1000240L,4245L,"Family","456785355678",new BankDetails(null,"State Bank","203460987654321","Public")));
			pensionservice.savePensionerDetails(new PensionerDetails(
					null,"Jil",date.parse("27/03/1997"),"1236409879",1002340L,4235L,"Self","456784098765",new BankDetails(null,"ICICI Bank","343471234509876","Private")));
	*/
		};
	}
	
}
