package com.radlly.util.mq.activeMq;





//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.Destination;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.ActiveMQMessageProducer;
//import org.apache.activemq.MessageTransformer;

public class QueueSenderTest {
	public static void main(String[] args) throws Exception {
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//			//	"tcp://localhost:61616");
//		    		"tcp://172.16.2.82:61616");
//				
//			//	"tcp://172.16.2.150:51512");
//			//	"failover:(tcp://172.16.2.151:61616,tcp://127.0.0.1:61616)?randomize=true");
//		Connection connection = connectionFactory.createConnection();
//		connection.start();
//		Session session = connection.createSession(Boolean.TRUE,
//				Session.AUTO_ACKNOWLEDGE);//消息确认 JMSDeliveryMode
//		Destination destination = session.createQueue("my-queue6");
//		
//		ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(destination);
//		for (int i = 0; i < 50; i++) {
//			TextMessage message = session.createTextMessage("message成都--" + i);
//			
////			MapMessage message = session.createMapMessage();
////			message.setStringProperty("extra"+i, "okok");
////
////					
////			message.setString("message---"+i, "my map message AAA=="+i);
//			/*producer.setTransformer(new MessageTransformer() {
//				public Message producerTransform(Session session, MessageProducer producer,
//						Message msg) throws JMSException {
//					MapMessage message = session.createMapMessage();
//					message.setString(((TextMessage)msg).getText(), "my map message AAA=="+((TextMessage)msg).getText());
//					message.setStringProperty("extra", "okok");
//					return message;
//				}
//				public Message consumerTransform(Session arg0, MessageConsumer arg1,
//						Message arg2) throws JMSException {
//					return null;
//				}
//			});
//			*/
//			//			Thread.sleep(1000);
//			// 通过消息生产者发出消息
//			producer.send(message);
//		}
//		
//		session.commit();
//		session.close();
//		connection.close();
//		System.out.println("发送完毕");
	}
}