package de.marcoaust.prometheus;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

@ConfigurationProperties("endpoints.prometheus")
class PrometheusEndpoint extends AbstractEndpoint<String> {

  private static final Logger log = LoggerFactory.getLogger(PrometheusEndpoint.class);

  private final CollectorRegistry collectorRegistry;

  public PrometheusEndpoint(CollectorRegistry collectorRegistry) {
    super("prometheus", true /* sensitive */);
    this.collectorRegistry = collectorRegistry;
  }

  @Override
  public String invoke() {
    try {
      Writer writer = new StringWriter();
      TextFormat.write004(writer, collectorRegistry.metricFamilySamples());
      return writer.toString();
    } catch (IOException e) {
      log.error("Writing metrics failed", e);
    }
    return "";
  }
}