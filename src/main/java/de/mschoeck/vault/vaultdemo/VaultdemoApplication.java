package de.mschoeck.vault.vaultdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.vault.core.env.VaultPropertySource;

@SpringBootApplication
public class VaultdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaultdemoApplication.class, args);
	}

}
