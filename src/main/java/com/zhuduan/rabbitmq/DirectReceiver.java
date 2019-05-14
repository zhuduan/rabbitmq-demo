package com.zhuduan.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * direct
 *
 * @author Haifeng.Zhu
 * created at 5/13/19
 */
@Component
public class DirectReceiver implements ChannelAwareMessageListener {
    
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("get message from Message Object: " + new String(body));
        Thread.sleep(5000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("done");
    }
}
