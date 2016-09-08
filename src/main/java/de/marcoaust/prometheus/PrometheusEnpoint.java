package de.marcoaust.prometheus;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

@Component
class PrometheusEnpoint implements Endpoint<String> {

  private static final Logger log = LoggerFactory.getLogger(PrometheusEnpoint.class);

  private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;

  @Override
  public String getId() {
    return "prometheus";
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isSensitive() {
    return true;
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