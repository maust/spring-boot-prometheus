package de.marcoaust.prometheus;

import java.util.Collection;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class PrometheusConfiguration {
  private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;

  @Bean
  public SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
    SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector(publicMetrics);
    springBootMetricsCollector.register(collectorRegistry);
    return springBootMetricsCollector;
  }

  @Configuration
  @ConditionalOnClass(DefaultExports.class)
  @ConditionalOnProperty(prefix = "prometheus.hotspot", name = "enabled", matchIfMissing = true)
  static class HotspotMetricsExporter {
    public HotspotMetricsExporter() {
      DefaultExports.initialize();
    }
  }

  @Bean
  public PrometheusEndpoint prometheusEndpoint() {
    return new PrometheusEndpoint(collectorRegistry);
  }
}
