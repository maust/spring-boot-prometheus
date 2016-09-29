package de.marcoaust.prometheus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.net.UnknownHostException;

@SpringBootApplication
@EnablePrometheusEndpoint
public class TestApplication {

    public static void main(String[] args) throws UnknownHostException {
        new SpringApplicationBuilder(TestApplication.class).web(true).run(args);
    }

}
