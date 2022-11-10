package ua.edu.npu.gradessynchronizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GradesSynchronizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradesSynchronizerApplication.class, args);
	}

}
