package br.com.caelum.jms;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidor_Topico_Estoque_Selector {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
					conexao.setClientID("estoque");
					conexao.start();

		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");

		MessageConsumer consumer = session.createDurableSubscriber(topico, "sou_assinante", "ebook=false", false);

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
