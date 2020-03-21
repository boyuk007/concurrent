package rabbitmq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 当消息过期还没有消费者消费时，消息会自动进入到死信队列
 */
public class Producer_Dlx {
    public static String EXCHANGE_NAME = "exchange_dlx";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        //没有消费者就会丢失；只有队列有存储能力
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//分发模式

        String msg = "hello exchange dlx";
        String routingKey = "dlx";

        //定义失效时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                                            .expiration("10000")
                                            .deliveryMode(2)
                                            .contentEncoding("UTF-8")
                                            .build();

        channel.basicPublish( EXCHANGE_NAME,routingKey ,properties, msg.getBytes());
        System.out.println("生产者发出：" + msg);

        channel.close();
        connection.close();
    }
}
