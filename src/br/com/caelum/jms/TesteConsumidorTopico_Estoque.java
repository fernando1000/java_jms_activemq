package br.com.caelum.jms;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopico_Estoque {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
					conexao.setClientID("estoque");
					conexao.start();

		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");

		MessageConsumer consumer = session.createDurableSubscriber(topico, "sou_assinante");

		consumer.setMessageListener(new MessageListener(){
		    @Override
		    public void onMessage(Message message){
		    	
		    	ObjectMessage objectMessage = (ObjectMessage) message;
		        try {
		        	
		        	Pedido pedido = (Pedido) objectMessage.getObject();
		        	
					System.out.println(pedido.getValorTotal());
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
