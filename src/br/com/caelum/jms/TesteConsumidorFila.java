package br.com.caelum.jms;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();

		conexao.start();

		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);

		//Message message = consumer.receive();
		//System.out.println("Recebendo msg: " + message);

		consumer.setMessageListener(new MessageListener(){
		    @Override
		    public void onMessage(Message message){
		    	
		    	TextMessage textMessage = (TextMessage) message;
		        try {
					System.out.println(textMessage.getText());
				} 
		        catch (JMSException e) {
					e.printStackTrace();
				}
		    }

		});
		
		
		new Scanner(System.in).nextLine();

		session.close();
		conexao.close();    
		context.close();
	}

}
