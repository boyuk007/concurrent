package rabbitmq.work_queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Simple {
    public static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //每个消息收到确认之前，不发送下一个消息
        channel.basicQos(1);

        for (int i = 0; i < 50; i++) {
            String msg = "hello work " + i;

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("生产者发出：" + msg);
            Thread.sleep(20);
        }

        channel.close();
        connection.close();
    }
}
