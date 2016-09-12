package de.marcoaust.prometheus;

import java.util.Collection;

import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.context.annotation.Bean;

class PrometheusConfiguration {
  private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;

  @Bean
  public SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
    SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector(publicMetrics);
    springBootMetricsCollector.register(collectorRegistry);
    return springBootMetricsCollector;
  }
    }
  @Bean
  public PrometheusEndpoint prometheusEndpoint() {
    return new PrometheusEndpoint(collectorRegistry);
  }
}
