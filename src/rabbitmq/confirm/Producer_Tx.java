package rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 单条
 */
public class Producer_Tx {
    public static String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple queue tx";
        channel.confirmSelect();//事务

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("生产者发出：" + msg);

        if (!channel.waitForConfirms()){
            System.out.println("生产者发送失败");
        }else {
            System.out.println("生产者发送成功");
        }
        channel.close();
        connection.close();
    }
}
