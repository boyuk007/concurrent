package rabbitmq.Tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Tx {
    public static String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple queue tx";

        try {
            channel.txSelect();//事务

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("生产者发出：" + msg);
            int i = 1 / 0;
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("生产者发生异常");
        }

        channel.close();
        connection.close();
    }
}
