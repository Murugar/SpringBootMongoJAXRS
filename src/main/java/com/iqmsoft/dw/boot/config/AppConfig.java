package com.iqmsoft.dw.boot.config;

import java.util.Collections;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.metrics.MetricsFeature;
import org.apache.cxf.metrics.codahale.CodahaleMetricsProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.iqmsoft.dw.boot.rs.PeopleRestService;

@Configuration
@EnableMongoRepositories(basePackages = "com.iqmsoft.dw.boot.repos")
@ComponentScan(basePackages = "com.iqmsoft.dw.boot")
@EnableAutoConfiguration
@Import(PrometheusConfig.class)
public class AppConfig {
	@Bean(destroyMethod = "destroy")
	public Server jaxRsServer(final Bus bus) {
		final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();

		factory.setServiceBean(peopleRestService());
		factory.setProvider(new JacksonJsonProvider());
		factory.setBus(bus);
		factory.setAddress("/");
		factory.setFeatures(Collections.singletonList(
		    new MetricsFeature(new CodahaleMetricsProvider(bus))
		));
		factory.setProperties(
			Collections.singletonMap(
				"org.apache.cxf.management.service.counter.name", 
				"cxf-services."
			)
		);

		return factory.create();
	}
	
	@Bean
	public PeopleRestService peopleRestService() {
		return new PeopleRestService();
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public JmxReporter jmxReporter() {
		return JmxReporter.forRegistry(metricRegistry()).build();
	}
	
	@Bean
	public MetricRegistry metricRegistry() {
		return new MetricRegistry();
	}
}
