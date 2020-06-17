package scottxuan.cloud.core.amqp;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaoxuan
 */
@Configuration
public class QueueConfig {

    private static final String delay_message_type = "x-delayed-message";
    private static final String delay_exchange = "delay-exchange";
    private static final String delay_message_args_key = "x-delayed-type";
    private static final String delay_message_args_value = "direct";

    /**
     * delay exchange
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put(delay_message_args_key, delay_message_args_value);
        return new CustomExchange(delay_exchange, delay_message_type,true, false,args);
    }
}
