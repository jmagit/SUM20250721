package com.example.infraestructure.messages;

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

import com.example.contracts.domain.distributed.FacturasCommand;

/**
 * Session Bean implementation class FacturasReceiver
 */
@Stateless
@LocalBean
public class FacturasQueue implements FacturasCommand {
    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/respuestas")
    private Queue queue;

    @Override
	public void send(String text) {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSProducer producer = context.createProducer();
            producer.send(queue, text);
            System.out.println(getClass().getSimpleName() + ": Mensaje '" + text + "' enviado a " + queue.getQueueName());
        } catch (JMSException e) {
            System.err.println(getClass().getSimpleName() + ": Error al enviar el mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
	public String receive(long timeoutMillis) {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue);
            Message message = consumer.receive(timeoutMillis);

            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                System.out.println(getClass().getSimpleName() + ": Mensaje recibido de OutputQueue: '" + text + "'");
                return text;
            } else if (message == null) {
            	return null;
//                System.out.println(getClass().getSimpleName() + ": No se recibió ningún mensaje dentro del tiempo de espera.");
//                return "No message received";
            } else {
                System.out.println(getClass().getSimpleName() + ": Recibido un mensaje no de texto: " + message.getClass().getName());
                return "Non-text message received";
            }
        } catch (JMSException e) {
            System.err.println(getClass().getSimpleName() + ": Error al recibir el mensaje: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
