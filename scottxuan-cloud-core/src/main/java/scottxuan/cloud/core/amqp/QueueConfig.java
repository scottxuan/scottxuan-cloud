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

    private static final String DELAY_MESSAGE_TYPE = "x-delayed-message";
    private static final String DELAY_EXCHANGE = "delay-exchange";
    private static final String DELAY_MESSAGE_ARGS_KEY = "x-delayed-type";
    private static final String DELAY_MESSAGE_ARGS_VALUE = "direct";

    /**
     * delay exchange
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put(DELAY_MESSAGE_ARGS_KEY, DELAY_MESSAGE_ARGS_VALUE);
        return new CustomExchange(DELAY_EXCHANGE, DELAY_MESSAGE_TYPE,true, false,args);
    }
}
