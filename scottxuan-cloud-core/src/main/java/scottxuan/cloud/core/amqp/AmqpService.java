package scottxuan.cloud.core.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author : zhaoxuan
 */
@Component
public class AmqpService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private CustomExchange delayExchange;

    private static AmqpService amqpService;

    @PostConstruct
    public void init() {
        amqpService = this;
    }
    /**
     * simple message
     * @param queue
     * @param message
     */
    public static void sendSimpleQueue(String queue,Object message){
        amqpService.rabbitAdmin.declareQueue(new Queue(queue));
        amqpService.rabbitTemplate.convertAndSend(queue,message);
    }

    /**
     * direct message
     * @param exchange
     * @param routingKey
     * @param message
     */
    public static void sendDirectQueue(String exchange,String routingKey,Object message){
        amqpService.rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }

    /**
     * fanout message
     * @param exchange
     * @param message
     */
    public static void sendFanoutQueue(String exchange,Object message){
        amqpService.rabbitTemplate.convertAndSend(exchange,"",message);
    }

    /**
     * topic message
     * @param exchange
     * @param routingKey
     * @param message
     */
    public static void sendTopicQueue(String exchange,String routingKey,Object message){
        amqpService.rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }

    /**
     * 延时消息发送
     * @param queue         队列名称
     * @param message       消息体
     * @param delayTimeMs     延时时间,单位ms
     */
    public static void sendDelayQueue(String queue,Object message,long delayTimeMs){
        Queue q = new Queue(queue);
        amqpService.rabbitAdmin.declareQueue(q);
        Binding binding = BindingBuilder.bind(q).to(amqpService.delayExchange).with(queue).noargs();
        amqpService.rabbitAdmin.declareBinding(binding);
        amqpService.rabbitTemplate.convertAndSend(
                amqpService.delayExchange.getName(),queue,message,
                msg -> {
                    if (delayTimeMs >= 0){
                        msg.getMessageProperties().setHeader("x-delay",delayTimeMs);
                    }else{
                        msg.getMessageProperties().getHeaders().remove("x-delay");
                    }
                    return msg;
                });
    }
}
