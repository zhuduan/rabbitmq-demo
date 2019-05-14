package com.zhuduan.rabbitmq.configs;

import com.zhuduan.rabbitmq.DirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * configs
 *
 * @author Haifeng.Zhu
 * created at 4/1/19
 */
@EnableRabbit
@Configuration
public class RabbitConfig {
    
    public static final String DIRECT_QUEUE = "direct";
    
    public static final String TOPIC_EXCHAGE = "topic1";
    public static final String QUEUE_1 = "test1";
    public static final String QUEUE_2 = "test2";
    public static final String QUEUE_3 = "test3";
    
    public static final String TOPIC_ROUTE_KEY = "route1";
    public static final String TOPIC_ROUTE_KEY_2 = "route2";
    
    @Bean
    Queue directQueue(){
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    Queue queue1(){
        return new Queue(QUEUE_1, true);
    }

    @Bean
    Queue queue2(){
        return new Queue(QUEUE_2, true);
    }

    @Bean
    Queue queue3(){
        return new Queue(QUEUE_3, true);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHAGE);
    }
    
    @Bean
    Binding bindingQueue1WithTopicKey1(){
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(TOPIC_ROUTE_KEY);
    }

    @Bean
    Binding bindingQueue2WithTopicKey1(){
        return BindingBuilder.bind(queue2()).to(topicExchange()).with(TOPIC_ROUTE_KEY);
    }

    @Bean
    Binding bindingQueue3WithTopicKey2(){
        return BindingBuilder.bind(queue3()).to(topicExchange()).with(TOPIC_ROUTE_KEY_2);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1",5672);

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(directQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO); //设置确认模式手工确认
        container.setMessageListener(new DirectReceiver());
        return container;
    }
}
