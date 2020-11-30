package com.gb.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-activiemq.xml")
public class TestQuene {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueDestination;
	
	@Test
	public void testQueueProducer() {
		// 第一步：初始化一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-activiemq.xml");
		// 第二步：从容器中获得JMSTemplate对象。
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 第三步：从容器中获得一个Destination对象
		Queue queue = (Queue) applicationContext.getBean("queueDestination");
		// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
		jmsTemplate.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("spring activemq test");
				return textMessage;
			}
		});
	}
	
	@Test
	public void testQueueProducer1() {
//		// 第一步：初始化一个spring容器
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-activiemq.xml");
//		// 第二步：从容器中获得JMSTemplate对象。
//		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//		// 第三步：从容器中获得一个Destination对象
//		Queue queue = (Queue) applicationContext.getBean("queueDestination");
		// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
		jmsTemplate.send(queueDestination, session -> {
			TextMessage textMessage = session.createTextMessage("spring activemq test");
			return textMessage;
		});
	}
	
}
