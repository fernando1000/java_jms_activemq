package br.com.caelum.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();

		conexao.start();

		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		//Pedido pedido = new PedidoFactory().geraPedidoComValores();

		//StringWriter writer = new StringWriter();
		//JAXB.marshal(pedido, writer);
		//String xml = writer.toString();

		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		Message message = session.createObjectMessage(pedido);
		
		//Message message = session.createTextMessage(xml);
		//Message message = session.createTextMessage("<pedido><id>88888888</id></pedido>");
		producer.send(message);
		
		session.close();
		conexao.close();    
		context.close();
	}

}
