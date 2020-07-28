package com.example.rabbitmq.config;

import com.example.rabbitmq.component.UserOrderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steadyjack on 2018/8/20.
 */
@Configuration
public class RabbitmqConfig {

    private static final Logger log= LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置消息序列化机制
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者的数量(默认为1)
        factory.setConcurrentConsumers(1);
        //设置最大并发消费者数量
        factory.setMaxConcurrentConsumers(1);
        //设置消费者一次从队列获取消息的数量(缓存在client)
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        //手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        //若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
        //每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，那么写生产者的时候不能再写confirm-callback和return-callback
        //使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        /**
         * 如果消息没有到exchange,则confirm回调,ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    //TODO：基本消息模型构建

    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(env.getProperty("basic.info.mq.exchange.name"), true,false);
    }

    @Bean(name = "basicQueue")
    public Queue basicQueue(){
        return new Queue(env.getProperty("basic.info.mq.queue.name"), true);
    }

    @Bean
    public Binding basicBinding(){



        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("basic.info.mq.routing.key.name"));
    }



    //TODO：商品抢单消息模型创建
    @Bean
    public DirectExchange robbingExchange(){
        return new DirectExchange(env.getProperty("product.robbing.mq.exchange.name"), true,false);
    }

    @Bean(name = "robbingQueue")
    public Queue robbingQueue(){
        return new Queue(env.getProperty("product.robbing.mq.queue.name"), true);
    }

    @Bean
    public Binding robbingBinding(){
        return BindingBuilder.bind(robbingQueue()).to(robbingExchange()).with(env.getProperty("product.robbing.mq.routing.key.name"));
    }




    //TODO：并发配置-消息确认机制-listener

    @Bean(name = "simpleQueue")
    public Queue simpleQueue(){
        return new Queue(env.getProperty("simple.mq.queue.name"),true);
    }

    @Bean
    public TopicExchange simpleExchange(){
        return new TopicExchange(env.getProperty("simple.mq.exchange.name"),true,false);
    }

    @Bean
    public Binding simpleBinding(){
        return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with(env.getProperty("simple.mq.routing.key.name"));
    }

    //TODO：用户商城抢单实战

    @Bean(name = "userOrderQueue")
    public Queue userOrderQueue(){
        return new Queue(env.getProperty("user.order.queue.name"),true);
    }

    @Bean
    public TopicExchange userOrderExchange(){
        return new TopicExchange(env.getProperty("user.order.exchange.name"),true,false);
    }

    @Bean
    public Binding userOrderBinding(){
        return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange()).with(env.getProperty("user.order.routing.key.name"));
    }

    //TODO：系统日志消息模型

    @Bean(name = "logSystemQueue")
    public Queue logSystemQueue(){
        return new Queue(env.getProperty("log.system.queue.name"),true);
    }

    @Bean
    public TopicExchange logSystemExchange(){
        return new TopicExchange(env.getProperty("log.system.exchange.name"),true,false);
    }

    @Bean
    public Binding logSystemBinding(){
        return BindingBuilder.bind(logSystemQueue()).to(logSystemExchange()).with(env.getProperty("log.system.routing.key.name"));
    }


    //TODO：用户操作日志消息模型

    @Bean(name = "logUserQueue")
    public Queue logUserQueue(){
        return new Queue(env.getProperty("log.user.queue.name"),true);
    }

    @Bean
    public DirectExchange logUserExchange(){
        return new DirectExchange(env.getProperty("log.user.exchange.name"),true,false);
    }

    @Bean
    public Binding logUserBinding(){
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(env.getProperty("log.user.routing.key.name"));
    }


    //TODO：发送邮件消息模型
    @Bean
    public Queue mailQueue(){
        return new Queue(env.getProperty("mail.queue.name"),true);
    }

    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(env.getProperty("mail.exchange.name"),true,false);
    }

    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(env.getProperty("mail.routing.key.name"));
    }


    //TODO：死信队列消息模型

    //创建死信队列
    @Bean
    public Queue simpleDeadQueue(){
        Map<String, Object> args=new HashMap();

        args.put("x-dead-letter-exchange", env.getProperty("simple.dead.exchange.name"));
        args.put("x-dead-letter-routing-key", env.getProperty("simple.dead.routing.key.name"));
        args.put("x-message-ttl", 10000);

        return new Queue(env.getProperty("simple.dead.queue.name"),true,false,false,args);
    }

    //绑定死信队列-面向生产端
    @Bean
    public TopicExchange simpleDeadExchange(){
        return new TopicExchange(env.getProperty("simple.produce.exchange.name"),true,false);
    }

    @Bean
    public Binding simpleDeadBinding(){
        return BindingBuilder.bind(simpleDeadQueue()).to(simpleDeadExchange()).with(env.getProperty("simple.produce.routing.key.name"));
    }


    //创建并绑定实际监听消费队列
    @Bean
    public Queue simpleDeadRealQueue(){
        return new Queue(env.getProperty("simple.dead.real.queue.name"),true);
    }

    @Bean
    public TopicExchange simpleDeadRealExchange(){
        return new TopicExchange(env.getProperty("simple.dead.exchange.name"),true,false);
    }

    @Bean
    public Binding simpleDeadRealBinding(){
        return BindingBuilder.bind(simpleDeadRealQueue()).to(simpleDeadRealExchange()).with(env.getProperty("simple.dead.routing.key.name"));
    }




    //TODO：用户下单支付超时死信队列模型

    @Bean
    public Queue userOrderDeadQueue(){
        Map<String, Object> args=new HashMap();
        args.put("x-dead-letter-exchange",env.getProperty("user.order.dead.exchange.name"));
        args.put("x-dead-letter-routing-key",env.getProperty("user.order.dead.routing.key.name"));
        args.put("x-message-ttl",10000);

        return new Queue(env.getProperty("user.order.dead.queue.name"),true,false,false,args);
    }

    //绑定死信队列-面向生产端
    @Bean
    public TopicExchange userOrderDeadExchange(){
        return new TopicExchange(env.getProperty("user.order.dead.produce.exchange.name"),true,false);
    }

    @Bean
    public Binding userOrderDeadBinding(){
        return BindingBuilder.bind(userOrderDeadQueue()).to(userOrderDeadExchange()).with(env.getProperty("user.order.dead.produce.routing.key.name"));
    }

    //创建并绑定实际监听消费队列-面向消费端
    @Bean
    public Queue userOrderDeadRealQueue(){
        return new Queue(env.getProperty("user.order.dead.real.queue.name"),true);
    }


    @Bean
    public TopicExchange userOrderDeadRealExchange(){
        return new TopicExchange(env.getProperty("user.order.dead.exchange.name"));
    }

    @Bean
    public Binding userOrderDeadRealBinding(){
        return BindingBuilder.bind(userOrderDeadRealQueue()).to(userOrderDeadRealExchange()).with(env.getProperty("user.order.dead.routing.key.name"));
    }


    //TODO：死信队列动态设置TTL消息模型

    @Bean
    public Queue dynamicDeadQueue(){
        Map<String, Object> args=new HashMap();
        args.put("x-dead-letter-exchange",env.getProperty("dynamic.dead.exchange.name"));
        args.put("x-dead-letter-routing-key",env.getProperty("dynamic.dead.routing.key.name"));

        return new Queue(env.getProperty("dynamic.dead.queue.name"),true,false,false,args);
    }

    @Bean
    public TopicExchange dynamicDeadExchange(){
        return new TopicExchange(env.getProperty("dynamic.dead.produce.exchange.name"),true,false);
    }

    @Bean
    public Binding dynamicDeadBinding(){
        return BindingBuilder.bind(dynamicDeadQueue()).to(dynamicDeadExchange()).with(env.getProperty("dynamic.dead.produce.routing.key.name"));
    }


    @Bean
    public Queue dynamicDeadRealQueue(){
        return new Queue(env.getProperty("dynamic.dead.real.queue.name"),true);
    }


    @Bean
    public TopicExchange dynamicDeadRealExchange(){
        return new TopicExchange(env.getProperty("dynamic.dead.exchange.name"));
    }

    @Bean
    public Binding dynamicDeadRealBinding(){
        return BindingBuilder.bind(dynamicDeadRealQueue()).to(dynamicDeadRealExchange()).with(env.getProperty("dynamic.dead.routing.key.name"));
    }

    @Autowired
    private UserOrderListener userOrderListener;

    @Bean
    public SimpleMessageListenerContainer listenerContainerUserOrder(@Qualifier("userOrderQueue") Queue userOrderQueue){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //container.setMessageConverter(new Jackson2JsonMessageConverter());

        //TODO：并发配置
        container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",Integer.class));
        container.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",Integer.class));
        container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",Integer.class));

        //TODO:消息确认机制
        container.setQueues(userOrderQueue);
        container.setMessageListener(userOrderListener);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return container;
    }

    @Bean
    public Queue testDirectQueue(){return new Queue("testDirectQueue", true); }

    /**
     * 直连型交换机（direct exchange）是根据消息携带的路由键（routing key）将消息投递给对应队列的
     * @return
     */
    @Bean
    public DirectExchange testDirectExchange(){return new DirectExchange("testDirectExchange"); }

    @Bean
    public Binding testDirectBinding(){ return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("test.direct.routing.key"); }

    @Bean
    public Queue testFanOutQueue(){ return new Queue("testFanOutQueue", true); }

    @Bean
    public Queue testFanOutQueue2(){return new Queue("testFanOutQueue2", true); }

    /**
     * 扇形交换机(fanout exchange) 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    public FanoutExchange testFanOutExchange(){ return new FanoutExchange("testFanOutExchange"); }

    @Bean
    public Binding testFanOutBinding(){return BindingBuilder.bind(testFanOutQueue()).to(testFanOutExchange()); }

    @Bean
    public Binding testFanOutBinding2(){return BindingBuilder.bind(testFanOutQueue2()).to(testFanOutExchange()); }

    @Bean
    public SimpleRabbitListenerContainerFactory ConsumeAckListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置消息序列化机制
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者的数量(默认为1)
        factory.setConcurrentConsumers(1);
        //设置最大并发消费者数量
        factory.setMaxConcurrentConsumers(1);
        //设置消费者一次从队列获取消息的数量(缓存在client)
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        //手动确认消息已被消费
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}