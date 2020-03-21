package rabbitmq.work_queue;

import com.rabbitmq.client.*;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Simple {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //保证一次只分发一个
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("消费者1收到：" + msgString);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
//        boolean autoAck = true;//自动应答(一旦消息分配给了消费者，就会从内存删除)
        boolean autoAck = false;//手动模式，如果消费者挂了，会转发给别的消费者；消费者收到回执，才删除消息
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
