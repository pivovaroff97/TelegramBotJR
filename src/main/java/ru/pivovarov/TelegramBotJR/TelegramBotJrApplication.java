package ru.pivovarov.TelegramBotJR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TelegramBotJrApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotJrApplication.class, args);
	}

}
