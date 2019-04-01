package com.zhuduan.rabbitmq.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * configs
 *
 * @author Haifeng.Zhu
 * created at 4/1/19
 */
@Configuration
public class RabbitConfig {
    
    public static final String DIRECT_QUEUE = "direct";
    
    public static final String TOPIC_EXCHAGE = "topic1";
    public static final String TOPIC_QUEUE_1 = "topic1_queue1";
    public static final String TOPIC_QUEUE_2 = "topic1_queue2";
    
    @Bean
    public Queue directQueue(){
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHAGE);
    }
}
