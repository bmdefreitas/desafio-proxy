package br.com.bmdefreitas.proxy.proxyreverso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan 
public class ProxyReversoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyReversoApplication.class, args);
	}
}
