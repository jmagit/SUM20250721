package com.example.infraestructure.events;

import java.util.Optional;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import com.example.contracts.domain.distributed.SensoresEvent;

@Stateless
@LocalBean
public class SensoresTopic implements SensoresEvent {
    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/topic/sensores")
    private Topic topic;

    @Override
	public void send(String text) {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSProducer producer = context.createProducer();
            producer.send(topic, text);
            System.out.println(getClass().getSimpleName() + ": Evento '" + text + "' enviado a " + topic.getTopicName());
        } catch (JMSException e) {
            System.err.println(getClass().getSimpleName() + ": Error al enviar el evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
	public Optional<String> receive(long timeoutMillis) {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSConsumer consumer = context.createConsumer(topic);
            Message message = consumer.receive(timeoutMillis);

            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                return Optional.of(text);
            } else if (message == null) {
            	return Optional.empty();
            } else {
            	throw new JMSException("Received a non-text message: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(getClass().getSimpleName() + ": Error al recibir el evento: " + e.getMessage(), e);
        }
    }
}
