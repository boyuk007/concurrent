package rabbitmq.confirm;

import com.rabbitmq.client.*;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Tx {
    private static final String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("消费者收到：" + msgString);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
