package br.com.aroma.aroma_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AromaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AromaDeliveryApplication.class, args);
	}

}
