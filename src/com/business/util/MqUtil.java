package com.business.util;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

/** 
 * @author xbq  
 * @date 创建时间：2016-4-16 下午3:50:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class MqUtil {
	
	/**
     * 队列 MQ
     * @param dest
     * @param json
     * @param jmsTemplateNotice
     * @throws Exception
     */
	public void sendTopicMsg(String dest,String json,JmsTemplate jmsTemplateNotice) throws Exception {
		// 设置目的地
		if (!Tools.isEmpty(dest)) {
			jmsTemplateNotice.setDefaultDestination(new ActiveMQQueue(dest));
			// 发送MQ消息
			jmsTemplateNotice.convertAndSend(json);
		}
	}
	
}
