package com.zhuduan.rabbitmq;

import com.zhuduan.rabbitmq.configs.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * direct sender
 *
 * @author Haifeng.Zhu
 * created at 5/13/19
 */
@Component
public class DirectSender implements RabbitTemplate.ConfirmCallback {
    
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public DirectSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessage(String message){
        MessageProperties msgProp = new MessageProperties();
        msgProp.setMessageId(UUID.randomUUID().toString());
        Message warpMsg = new Message(message.getBytes(), new MessageProperties());
        this.rabbitTemplate.convertAndSend(null, RabbitConfig.DIRECT_QUEUE, warpMsg);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack==true){
            System.out.println("send success");
        } else {
            System.out.println("send fail");
        }
    }
}
