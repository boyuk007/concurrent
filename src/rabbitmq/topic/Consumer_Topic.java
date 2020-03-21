package rabbitmq.topic;

import com.rabbitmq.client.*;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Topic {
    private static final String QUEUE_NAME = "exchange_queue_topic_1";
    public static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        //routingKey
        //#：匹配多个；*：只匹配一个
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"good.#");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("消费者1收到：" + msgString);
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
