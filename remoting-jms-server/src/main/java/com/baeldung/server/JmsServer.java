package com.baeldung.server;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import com.baeldung.api.CabBookingService;

// TODO: Auto-generated Javadoc
/**
 * The Class JmsServer.
 */
@SpringBootApplication
public class JmsServer {

	/*
	 * This server needs to be connected to an ActiveMQ server. To quickly spin
	 * up an ActiveMQ server, you can use Docker.
	 * 
	 * docker run -p 61616:61616 -p 8161:8161 rmohr/activemq:5.14.3
	 */

	/**
	 * Booking service.
	 *
	 * @return the cab booking service
	 */
	@Bean
	CabBookingService bookingService() {
		return new CabBookingServiceImpl();
	}

	/**
	 * Queue.
	 *
	 * @return the queue
	 */
	@Bean
	Queue queue() {
		return new ActiveMQQueue("remotingQueue");
	}

	/**
	 * Exporter.
	 *
	 * @param implementation
	 *            the implementation
	 * @return the jms invoker service exporter
	 */
	@Bean
	JmsInvokerServiceExporter exporter(CabBookingService implementation) {
		JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
		exporter.setServiceInterface(CabBookingService.class);
		exporter.setService(implementation);
		return exporter;
	}

	/**
	 * Listener.
	 *
	 * @param factory
	 *            the factory
	 * @param exporter
	 *            the exporter
	 * @return the simple message listener container
	 */
	@Bean
	SimpleMessageListenerContainer listener(ConnectionFactory factory, JmsInvokerServiceExporter exporter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.setDestinationName("remotingQueue");
		container.setConcurrentConsumers(1);
		container.setMessageListener(exporter);
		return container;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(JmsServer.class, args);
	}

}
