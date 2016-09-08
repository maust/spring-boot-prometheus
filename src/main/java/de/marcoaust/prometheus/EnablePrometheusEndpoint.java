package de.marcoaust.prometheus;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PrometheusConfiguration.class)
public @interface EnablePrometheusEndpoint {

}
