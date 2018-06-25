package co.blastlab.cityhack;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static final String SELF_URI = "http://192.168.48.36";
	public static final String FACEBK_URI = "http://192.168.48.36:8090";

	@SuppressWarnings("unused")
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
