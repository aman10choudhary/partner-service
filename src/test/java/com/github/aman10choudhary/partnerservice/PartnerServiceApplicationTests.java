package com.github.aman10choudhary.partnerservice;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/*@SpringBootTest*/
class PartnerServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Ignore
	@Test
	public void applicationTest(){
		PartnerServiceApplication.main(new String[0]);
	}

}
