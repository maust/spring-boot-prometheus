package de.marcoaust.prometheus;

import java.util.*;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;

import io.prometheus.client.Collector;


class SpringBootMetricsCollector extends Collector {
  private final Collection<PublicMetrics> publicMetrics;

  SpringBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
    this.publicMetrics = publicMetrics;
  }

  @Override
  public List<MetricFamilySamples> collect() {
    Map<String, MetricFamilySamples> samples = new HashMap<String, MetricFamilySamples>();
    for (PublicMetrics publicMetrics : this.publicMetrics) {
      for (Metric<?> metric : publicMetrics.metrics()) {
        String name = Collector.sanitizeMetricName(metric.getName());
        double value = metric.getValue().doubleValue();
        MetricFamilySamples metricFamilySamples = new MetricFamilySamples(
                name, Type.GAUGE, name, Collections.singletonList(new MetricFamilySamples.Sample(
                    name, Collections.<String>emptyList(), Collections.<String>emptyList(), value)));
        samples.put(name, metricFamilySamples);
      }
    }
    return new ArrayList<MetricFamilySamples>(samples.values());
  }
}
