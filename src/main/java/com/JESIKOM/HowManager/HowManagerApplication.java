package com.JESIKOM.HowManager;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static javafx.application.Application.launch;


@SpringBootApplication
public class HowManagerApplication {

	public static void main(String[] args) {
		//Init le context Spring
		ConfigurableApplicationContext context = SpringApplication.run(HowManagerApplication.class, args);

		// Lancer JavaFX
		JavaFxApplicationSupport.setContext(context);
		launch(JavaFxApplicationSupport.class, args);

		// Fermer Spring quand JavaFX se ferme
		context.close();
	}

}
