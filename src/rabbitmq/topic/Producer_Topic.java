package rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Topic {
    public static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        //没有消费者就会丢失；只有队列有存储能力
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");//分发模式

        String msg = "hello exchange_topic ";

        channel.basicPublish( EXCHANGE_NAME,"good.add" ,null, msg.getBytes());
        System.out.println("生产者发出：" + msg);

        channel.close();
        connection.close();
    }
}
