package com.example.infraestructure.messages;

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
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: ContableListener
 */
//ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") // Modo de reconocimiento de mensaje
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/peticiones"), 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
		})
public class ContableListener implements MessageListener {
    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/respuestas")
    private Queue outputQueue;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String body = textMessage.getText();
				Thread.sleep(1000);
				body = "Factura de ContableListener a las " + LocalDateTime.now() + " para el " + body.toLowerCase();

                // --- Envío del mensaje a OutputQueue ---
                try (JMSContext context = connectionFactory.createContext()) {
                    JMSProducer producer = context.createProducer();
                    producer.send(outputQueue, body);
//					System.out.println("Nueva factura enviada por "+ getClass().getSimpleName() + ": " + body);
                }

            } else {
                System.out.println(getClass().getSimpleName() + ": Recibido un mensaje no de texto: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.err.println(getClass().getSimpleName() + ": Error al procesar/reenviar mensaje: " + e.getMessage());
            e.printStackTrace();
         } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
