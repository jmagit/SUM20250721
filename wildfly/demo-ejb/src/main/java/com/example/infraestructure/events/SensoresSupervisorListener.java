package com.example.infraestructure.events;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: SensoresSupervisorListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationLookup", propertyValue = "java:/jms/topic/sensores"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")
		}, 
		mappedName = "java:/jms/topic/sensores")
public class SensoresSupervisorListener implements MessageListener {
    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
	
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
				System.out.println("Evento " + message.getJMSMessageID() + " procesado por " + getClass().getSimpleName() 
						+ ": " + ((TextMessage) message).getText());
            } else {
                System.out.println(getClass().getSimpleName() + ": Recibido un evento no de texto: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.err.println(getClass().getSimpleName() + ": Error al procesar/reenviar evento: " + e.getMessage());
            e.printStackTrace();
		}
    }

}
