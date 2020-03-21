package rabbitmq.dlx;

import com.rabbitmq.client.*;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Consumer_Dlx {
    private static final String QUEUE_NAME = "exchange_queue_dlx";
    public static String EXCHANGE_NAME = "exchange_dlx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        String routingKey = "dlx.#";
        HashMap<String, Object> argument = new HashMap<>();
        argument.put("x-dead-letter-exchange","dlx.exchange");

        channel.queueDeclare(QUEUE_NAME, false, false, false, argument);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("消费者1收到：" + msgString);
            }
        };

        //声明死信队列
        channel.exchangeDeclare("dlx.exchange","fanout",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
