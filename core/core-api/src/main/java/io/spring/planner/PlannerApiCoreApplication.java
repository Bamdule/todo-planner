package io.spring.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class PlannerApiCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlannerApiCoreApplication.class, args);
    }

}
