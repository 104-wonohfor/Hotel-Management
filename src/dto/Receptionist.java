package dto;

import com.rabbitmq.client.*;

public class Receptionist extends Person implements Runnable {
    private static final String EXCHANGE_NAME = "notifications";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String queueName; 

    public Receptionist() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        queueName = channel.queueDeclare().getQueue(); 

        channel.queueBind(queueName, EXCHANGE_NAME, "bookRoom");
        channel.queueBind(queueName, EXCHANGE_NAME, "callService");
    }

    @Override
    public void run() {
        try {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    