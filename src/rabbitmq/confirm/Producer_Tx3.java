package rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import rabbitmq.RabbitUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 异步
 */
public class Producer_Tx3 {
    public static String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple queue tx";
        channel.confirmSelect();//事务

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            //没问题的回执处理
            public void handleAck(long l, boolean b) throws IOException {
                if (b){
                    System.out.println("---handleAck---multiple");
                    confirmSet.headSet(l+1).clear();
                }else {
                    System.out.println("---handleAck---multiple  false");
                    confirmSet.remove(l);
                }
            }

            @Override
            //有问题的，回执处理
            public void handleNack(long l, boolean b) throws IOException {
                if (b){
                    System.out.println("---handleNack---multiple");
                    confirmSet.headSet(l+1).clear();
                }else {
                    System.out.println("---handleNack---multiple  false");
                    confirmSet.remove(l);
                }
            }
        });

        for (; ;) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("生产者发出：" + msg);
            confirmSet.add(seqNo);
        }

    }
}
