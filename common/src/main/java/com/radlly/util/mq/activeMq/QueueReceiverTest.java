package com.radlly.util.mq.activeMq;





//import javax.jms.Connection;
//import javax.jms.Destination;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.ActiveMQMessageConsumer;
//import org.apache.activemq.RedeliveryPolicy;

public class QueueReceiverTest {
	public static void main(String[] args) throws Exception {
//		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
//		//		"tcp://localhost:61616");
//				"tcp://172.16.2.82:61616");
////				"tcp://172.16.2.144:51511");
//		
//		RedeliveryPolicy policy = new RedeliveryPolicy();
//		policy.setMaximumRedeliveries(3);
//		cf.setRedeliveryPolicy(policy);
//		
//		Connection connection = cf.createConnection();
//		connection.start();
//		
////		Enumeration names = connection.getMetaData().getJMSXPropertyNames();
////		while(names.hasMoreElements()){
////			String name = (String)names.nextElement();
////			System.out.println("jmsx name==="+name);
////		}
//		
//
//		final Session session = connection.createSession(Boolean.TRUE,
//				Session.AUTO_ACKNOWLEDGE);
//		Destination destination = session.createQueue("my-queue6");
//
//		ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer)session.createConsumer(destination);
//		
//		
//		int i = 0;
//		while (i < 50) {
//			i++;
//			TextMessage message = (TextMessage) consumer.receive();
//			
//			
//			
//	//		MapMessage message = (MapMessage) consumer.receive();
//			
//			
//			
//			
//			
//			
////			if(i==2){
////				message.acknowledge();
////			}
////			
//		/*	System.out.println("收到消 息：" + message.getStringProperty("messageAA--" + i) 
//					+" , property=="+message.getStringProperty("extra"));*/
//			System.out.println(Thread.currentThread().getName()+"远程收到消 息：" + message.getText());
//			
//			session.commit();
//			
//			//i++;
//			
//		//	session.commit();
//		}
//		session.close();
//		connection.close();
	}

}
