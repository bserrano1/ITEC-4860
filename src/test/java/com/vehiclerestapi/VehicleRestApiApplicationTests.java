package com.vehiclerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VehicleRestApiApplicationTests {

	public static void main(String []args )
	{
		SpringApplication.run(VehicleRestApiApplication.class,args);
	}

}
