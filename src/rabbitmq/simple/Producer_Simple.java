package rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Simple {
    public static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("生产者发出：" + msg);

        channel.close();
        connection.close();
    }
}
