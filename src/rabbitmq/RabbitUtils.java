package rabbitmq;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtils {
    /**
     * 获取rabbitmq连接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("111.231.225.127");
        factory.setPort(5672);
        factory.setVirtualHost("/user");
        factory.setUsername("user");
        factory.setPassword("1234");

        return factory.newConnection();
    }
}
